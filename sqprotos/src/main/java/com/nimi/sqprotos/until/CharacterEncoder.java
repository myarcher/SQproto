package com.nimi.sqprotos.until;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2017/3/4.
 */

public abstract class CharacterEncoder {
    protected PrintStream pStream;

    public CharacterEncoder() {
    }

    protected abstract int bytesPerAtom();

    protected abstract int bytesPerLine();

    protected void encodeBufferPrefix(OutputStream outputstream) throws IOException {
        this.pStream = new PrintStream(outputstream);
    }

    protected void encodeBufferSuffix(OutputStream outputstream) throws IOException {
    }

    protected void encodeLinePrefix(OutputStream outputstream, int i) throws IOException {
    }

    protected void encodeLineSuffix(OutputStream outputstream) throws IOException {
        this.pStream.println();
    }

    protected abstract void encodeAtom(OutputStream var1, byte[] var2, int var3, int var4) throws IOException;

    protected int readFully(InputStream inputstream, byte[] abyte0) throws IOException {
        for(int i = 0; i < abyte0.length; ++i) {
            int j = inputstream.read();
            if(j == -1) {
                return i;
            }

            abyte0[i] = (byte)j;
        }

        return abyte0.length;
    }

    public void encode(InputStream inputstream, OutputStream outputstream) throws IOException {
        byte[] abyte0 = new byte[this.bytesPerLine()];
        this.encodeBufferPrefix(outputstream);

        while(true) {
            int j = this.readFully(inputstream, abyte0);
            if(j == 0) {
                break;
            }

            this.encodeLinePrefix(outputstream, j);

            for(int i = 0; i < j; i += this.bytesPerAtom()) {
                if(i + this.bytesPerAtom() <= j) {
                    this.encodeAtom(outputstream, abyte0, i, this.bytesPerAtom());
                } else {
                    this.encodeAtom(outputstream, abyte0, i, j - i);
                }
            }

            if(j < this.bytesPerLine()) {
                break;
            }

            this.encodeLineSuffix(outputstream);
        }

        this.encodeBufferSuffix(outputstream);
    }

    public void encode(byte[] abyte0, OutputStream outputstream) throws IOException {
        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte0);
        this.encode((InputStream)bytearrayinputstream, outputstream);
    }

    public String encode(byte[] abyte0) {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte0);
        String s = null;

        try {
            this.encode((InputStream)bytearrayinputstream, bytearrayoutputstream);
            s = bytearrayoutputstream.toString("8859_1");
            return s;
        } catch (Exception var6) {
            throw new Error("CharacterEncoder.encode internal error");
        }
    }

    private byte[] getBytes(ByteBuffer bytebuffer) {
        byte[] abyte0 = null;
        if(bytebuffer.hasArray()) {
            byte[] abyte1 = bytebuffer.array();
            if(abyte1.length == bytebuffer.capacity() && abyte1.length == bytebuffer.remaining()) {
                abyte0 = abyte1;
                bytebuffer.position(bytebuffer.limit());
            }
        }

        if(abyte0 == null) {
            abyte0 = new byte[bytebuffer.remaining()];
            bytebuffer.get(abyte0);
        }

        return abyte0;
    }

    public void encode(ByteBuffer bytebuffer, OutputStream outputstream) throws IOException {
        byte[] abyte0 = this.getBytes(bytebuffer);
        this.encode(abyte0, outputstream);
    }

    public String encode(ByteBuffer bytebuffer) {
        byte[] abyte0 = this.getBytes(bytebuffer);
        return this.encode(abyte0);
    }

    public void encodeBuffer(InputStream inputstream, OutputStream outputstream) throws IOException {
        byte[] abyte0 = new byte[this.bytesPerLine()];
        this.encodeBufferPrefix(outputstream);

        int j;
        do {
            j = this.readFully(inputstream, abyte0);
            if(j == 0) {
                break;
            }

            this.encodeLinePrefix(outputstream, j);

            for(int i = 0; i < j; i += this.bytesPerAtom()) {
                if(i + this.bytesPerAtom() <= j) {
                    this.encodeAtom(outputstream, abyte0, i, this.bytesPerAtom());
                } else {
                    this.encodeAtom(outputstream, abyte0, i, j - i);
                }
            }

            this.encodeLineSuffix(outputstream);
        } while(j >= this.bytesPerLine());

        this.encodeBufferSuffix(outputstream);
    }

    public void encodeBuffer(byte[] abyte0, OutputStream outputstream) throws IOException {
        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte0);
        this.encodeBuffer((InputStream)bytearrayinputstream, outputstream);
    }

    public String encodeBuffer(byte[] abyte0) {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte0);

        try {
            this.encodeBuffer((InputStream)bytearrayinputstream, bytearrayoutputstream);
        } catch (Exception var5) {
            throw new Error("CharacterEncoder.encodeBuffer internal error");
        }

        return bytearrayoutputstream.toString();
    }

    public void encodeBuffer(ByteBuffer bytebuffer, OutputStream outputstream) throws IOException {
        byte[] abyte0 = this.getBytes(bytebuffer);
        this.encodeBuffer(abyte0, outputstream);
    }

    public String encodeBuffer(ByteBuffer bytebuffer) {
        byte[] abyte0 = this.getBytes(bytebuffer);
        return this.encodeBuffer(abyte0);
    }
}
