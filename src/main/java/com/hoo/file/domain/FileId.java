package com.hoo.file.domain;

import com.hoo.common.domain.Authority;
import com.hoo.file.domain.exception.FileExtensionMismatchException;
import com.hoo.file.domain.exception.IllegalFileAuthorityDirException;
import lombok.Getter;

@Getter
public class FileId {
    private final String baseDir;
    private final Authority authority;
    private final FileType fileType;
    private final String realFileName;
    private final String fileSystemName;

    private FileId(String baseDir, Authority authority, FileType fileType, String realFileName, String fileSystemName) {
        this.baseDir = baseDir;
        this.authority = authority;
        this.fileType = fileType;
        this.realFileName = realFileName;
        this.fileSystemName = fileSystemName;
    }

    public static FileId create(String baseDir, Authority authority, FileType fileType, String realFileName, String fileSystemName) {
        if (baseDir.charAt(baseDir.length() - 1) == '/')
            baseDir = baseDir.substring(0, baseDir.length() - 1);

        FileId fileId = new FileId(baseDir, authority, fileType, realFileName, fileSystemName);

        fileId.verifyExtension(fileType, realFileName);

        return fileId;
    }

    public static FileId load(String parentDir, String realFileName, String fileSystemName) {
        String[] dirs = parentDir.split("/");

        String fileTypeDir = dirs[dirs.length - 1];
        FileType fileType = FileType.of(fileTypeDir);

        String authorityDir = dirs[dirs.length - 2];
        Authority authority = pathToAuthority(authorityDir, fileTypeDir);

        String baseDir = parentDir.split("/" + authorityDir)[0];

        return new FileId(baseDir, authority, fileType, realFileName, fileSystemName);
    }

    private static Authority pathToAuthority(String authorityDir, String fileTypeDir) {

        if (authorityDir.equalsIgnoreCase("public"))
            return Authority.PUBLIC_FILE_ACCESS;

        else if (authorityDir.equalsIgnoreCase("private"))
            if (fileTypeDir.equalsIgnoreCase("images"))
                return Authority.ALL_PRIVATE_IMAGE_ACCESS;

            else if (fileTypeDir.equalsIgnoreCase("audios"))
                return Authority.ALL_PRIVATE_AUDIO_ACCESS;

            else throw new IllegalFileAuthorityDirException(authorityDir + "/" + fileTypeDir);

        else throw new IllegalFileAuthorityDirException(authorityDir + "/" + fileTypeDir);
    }

    @Override
    public String toString() {
        return "[" + getPath() + "] " +
               "authority=" + authority +
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
        switch (authority) {
            case PUBLIC_FILE_ACCESS -> {
                return "public";
            }
            case ALL_PRIVATE_IMAGE_ACCESS, ALL_PRIVATE_AUDIO_ACCESS -> {
                return "private";
            }
            default -> throw new IllegalStateException("Unexpected value: " + authority);
        }
    }

    public String getDirectory() {
        return baseDir + "/" + getAuthorityPath() + "/" + fileType.getPath();
    }
}
