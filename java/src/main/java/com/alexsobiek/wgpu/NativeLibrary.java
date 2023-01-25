package com.alexsobiek.wgpu;

import com.alexsobiek.wgpu.exception.LibraryNotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class NativeLibrary {
    private final String path;
    private final String mappedName;
    private final File outputDir;

    public NativeLibrary(String name, File outputDir) {
        this.mappedName = System.mapLibraryName(name);
        String arch = System.getProperty("os.arch");
        this.path = "META-INF/natives/" + System.getProperty("os.name").toLowerCase().replace(" ", "") + "/" + (arch.contains("64") ? "x86_64" : arch.contains("86") ? "x86" : arch) + "/" + mappedName;

        this.outputDir = outputDir.toPath().resolve(this.path.replace("META-INF/natives/", "").replace("/" + mappedName, "")).toFile();
    }

    public static void load(String name, File outputDir) throws LibraryNotFoundException {
        new NativeLibrary(name, outputDir).load();
    }

    public void load() throws LibraryNotFoundException {
        if (!outputDir.exists()) outputDir.mkdirs();
        File lib = new File(outputDir, mappedName);

        try {
            if (!lib.exists() || (lib.exists() && !Arrays.equals(getDigest(getResource(path)), getDigest(new FileInputStream(lib))))) {
                replaceFromJar(lib, getResource(path));
            }
        } catch (IOException e) {
            throw new LibraryNotFoundException("Failed to load native library", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to get library digest", e);
        }
        System.load(lib.getAbsolutePath());
    }

    private InputStream getResource(String path) throws LibraryNotFoundException {
        InputStream is = NativeLibrary.class.getClassLoader().getResourceAsStream(path);
        if (is == null) throw new LibraryNotFoundException("Could not find lib " + path + " in jar");
        return is;
    }

    private void replaceFromJar(File output, InputStream is) throws IOException {
        Files.copy(is, output.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    private byte[] getDigest(InputStream is) throws IOException, NoSuchAlgorithmException {
        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead = 0;

        while (numRead != -1) {
            numRead = is.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        }

        is.close();
        return complete.digest();
    }
}