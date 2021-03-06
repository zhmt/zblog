package zb.blog.service;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zb.blog.BlogCfg;
import zb.blog.model.FileNode;
import zb.blog.util.ExceptionUtil;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhmt on 2017/6/3.
 */                         
@Service
public class FileService {
    public static final File fileRoot = newFile("./blogres/upload");
    public static final String strRoot = fileRoot.getPath();

    static {
        fileRoot.mkdirs();
    }

    @Autowired
    private BlogCfg blogCfg;

    /**
     * 
     * @param dir relative to @strRoot. /a/b
     * @return
     */
    public String mkdir(String dir) {
        File  f = newFile(strRoot + dir);
        checkDirUnderOrIsRoot(f);
        f.mkdirs();
        return getRelativeDir(f);
    }

    public String del(String dir) {
        File f = newFile(strRoot+dir);
        checkDirUnderOrIsRoot(f);

        //禁止删除根目录
        if(getPath(f).equals(getPath(fileRoot))) {
            throw new RuntimeException(blogCfg.strOnlySubdirectoryAllowed);
        }

        if(f.isDirectory())
            ExceptionUtil.castException(()->FileUtils.deleteDirectory(f)); 
        else
            if(!f.delete())
                throw new RuntimeException(blogCfg.strFailedToDeleteFile+" : "+dir);
        return getRelativeDir(f.getParentFile());
    }

    public void save(MultipartFile file, String dir) {
        File destFile = newFile(strRoot+dir+file.getOriginalFilename());
        checkDirUnderOrIsRoot(destFile);
        System.out.println(destFile);
        ExceptionUtil.castException(()->file.transferTo(destFile));
    }

    public List<FileNode> listDir (String dir) {
        File f = newFile(strRoot+dir);
        checkDirUnderOrIsRoot(f);
        List<FileNode> ret = new LinkedList<>();
        for(File one : f.listFiles()) {
            FileNode node = new FileNode(getRelativeDir(one),one.getName(),one.isDirectory(),one.isDirectory()?"jstree-folder":"jstree-file");
            ret.add(node);
        }
        return ret;
    }

    public static File newFile(String file) {
        return  ExceptionUtil.castException(()->{return new File(file).getCanonicalFile();});
    }

    private String getRelativeDir(File f) {
        return getPath(f).replace(getPath(fileRoot),"");
    }

    private String getPath(File f) {
         return f.getPath().replaceAll("\\\\","/");
    }

    private String getParentPath(File f) {
        return f.getParent().replaceAll("\\\\","/");
    }

    private void checkDirUnderOrIsRoot(File f) {
        String root =  getPath(fileRoot);
        String path = getPath(f);
        if(path.equals(root)) {
            return;
        }

        String parent =  getParentPath(f);
        if(parent.startsWith(root+"/")) {
            return;
        }
        if(parent.equals(root)) {
            return;
        }
        throw new RuntimeException(blogCfg.strOnlySubdirectoryAllowed);
    }

    public static void main(String[] args) {
        System.out.println(strRoot);
    }
}
