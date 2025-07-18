package com.hoo.file.domain;

import com.hoo.common.domain.Authority;
import com.hoo.file.domain.exception.FileExtensionMismatchException;
import com.hoo.file.domain.exception.IllegalFileAuthorityDirException;
import lombok.Getter;

@Getter
public class FileId {
    private final String baseDir;
    private final Authority ownerity;
    private final FileType fileType;
    private final String realFileName;
    private final String fileSystemName;

    private FileId(String baseDir, Authority ownerity, FileType fileType, String realFileName, String fileSystemName) {
        this.baseDir = baseDir;
        this.ownerity = ownerity;
        this.fileType = fileType;
        this.realFileName = realFileName;
        this.fileSystemName = fileSystemName;
    }

    public static FileId create(String baseDir, Authority ownerity, FileType fileType, String realFileName, String fileSystemName) {
        if (baseDir.charAt(baseDir.length() - 1) == '/')
            baseDir = baseDir.substring(0, baseDir.length() - 1);

        FileId fileId = new FileId(baseDir, ownerity, fileType, realFileName, fileSystemName);

        fileId.verifyExtension(fileType, realFileName);

        return fileId;
    }

    public static FileId load(String parentDir, String realFileName, String fileSystemName) {
        String[] dirs = parentDir.split("/");

        String fileTypeDir = dirs[dirs.length - 1];
        FileType fileType = FileType.of(fileTypeDir);

        String ownerityDir = dirs[dirs.length - 2];
        Authority ownerity = pathToAuthority(ownerityDir, fileTypeDir);

        String baseDir = parentDir.split("/" + ownerityDir)[0];

        return new FileId(baseDir, ownerity, fileType, realFileName, fileSystemName);
    }

    private static Authority pathToAuthority(String ownerityDir, String fileTypeDir) {

        if (ownerityDir.equalsIgnoreCase("public"))
            return Authority.PUBLIC_FILE_ACCESS;

        else if (ownerityDir.equalsIgnoreCase("private"))
            if (fileTypeDir.equalsIgnoreCase("images"))
                return Authority.ALL_PRIVATE_IMAGE_ACCESS;

            else if (fileTypeDir.equalsIgnoreCase("audios"))
                return Authority.ALL_PRIVATE_AUDIO_ACCESS;

            else throw new IllegalFileAuthorityDirException(ownerityDir + "/" + fileTypeDir);

        else throw new IllegalFileAuthorityDirException(ownerityDir + "/" + fileTypeDir);
    }

    @Override
    public String toString() {
        return "[" + getPath() + "] " +
               "ownerity=" + ownerity +
               ", fileType=" + fileType +
               ", realFileName='" + realFileName + '\'' +
               ", fileSystemName='" + fileSystemName;
    }

    public void verifyExtension(FileType fileType, String fileName) {
        switch (fileType) {
            case IMAGE -> {
                if (!fileName.matches(".*\\.(?:png|jpe?g|svg|gif)$"))
                    throw new FileExtensionMismatchException(fileType, fileName);
            }
            case AUDIO -> {
                if (!fileName.matches(".*\\.(?:mp3|wav)$"))
                    throw new FileExtensionMismatchException(fileType, fileName);
            }
            case VIDEO -> {
                throw new UnsupportedOperationException();
            }
        }
    }

    public String getPath() {
        return getDirectory() + "/" + fileSystemName;
    }

    public String getFilePath() {
        return "file:" + getPath();
    }

    private String getAuthorityPath() {
        switch (ownerity) {
            case PUBLIC_FILE_ACCESS -> {
                return "public";
            }
            case ALL_PRIVATE_IMAGE_ACCESS, ALL_PRIVATE_AUDIO_ACCESS -> {
                return "private";
            }
            default -> throw new IllegalStateException("Unexpected value: " + ownerity);
        }
    }

    public String getDirectory() {
        return baseDir + "/" + getAuthorityPath() + "/" + fileType.getPath();
    }
}
