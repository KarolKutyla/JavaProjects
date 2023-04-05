package zad1;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Futil implements FileVisitor<Path> {
    final File resultFile;
    final Charset CP1250charset = Charset.forName("Cp1250");
    final Charset UTF_8charset = Charset.forName("UTF-8");
    CharBuffer cBuf;

    static
    {

    }

    Futil(File resultFile)
    {
        this.resultFile = resultFile;
    }
    public static void processDir(String dirName, String resultFileName)
    {
        java.io.File resultFile = new File(resultFileName);
        try
        {
//            java.io.File dir = new java.io.File(dirName);
//            java.io.File resultFile = new File(resultFileName);
//            BufferedWriter bw = new BufferedWriter(new FileWriter(dir));
            Files.walkFileTree(Paths.get(dirName), new Futil(resultFile));
        } catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        //System.out.print("File visit: ");
        //System.out.println(file);
        //Files.write(resultPath, new String (Files.readAllBytes(file)).getBytes(Cp1250), StandardOpenOption.APPEND);
        //writeChannel(resultFile, readChannel(new File(file.toAbsolutePath().toString())));
        Charset cp1250 = Charset.forName("Cp1250");
        try(FileChannel fileChannel = FileChannel.open(file, StandardOpenOption.READ))
        {
            ByteBuffer buffer = ByteBuffer.allocate((int)fileChannel.size());
            fileChannel.read(buffer);
            buffer.flip();
            String content = cp1250.decode(buffer).toString();
            ByteBuffer resultBuffer = Charset.forName("UTF-8").encode(content);
            RandomAccessFile f = new RandomAccessFile(resultFile, "rw");
            FileChannel channel = f.getChannel();
            channel.position(channel.size());
            channel.write(resultBuffer);
            fileChannel.close();
            f.close();
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    byte[] readChannel(File filename) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filename);
        FileChannel fileChannel = fileInputStream.getChannel();
        int size = (int)fileChannel.size();
        ByteBuffer buffer = ByteBuffer.allocate(size);
        int nBytes = fileChannel.read(buffer);
        fileChannel.close();
        cBuf = CP1250charset.decode(buffer);
        System.out.println(cBuf.get(0));

        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return bytes;
    }

    void writeChannel(File fname, byte[] data) throws IOException {
        ByteBuffer buffer = UTF_8charset.encode(cBuf);
        FileOutputStream fileOutputStream = new FileOutputStream(resultFile, true);
        FileChannel fileChannel = fileOutputStream.getChannel();
        fileChannel.write(buffer);
        fileChannel.close();
    }
}
