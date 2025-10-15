package rpg.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * @author fhernandez
 *
 * @version $Revision: 1.2 $
 */
public class SerialBlob implements Blob, Serializable, Cloneable {
    private transient InputStream inStream;
    private long length;
    //private byte[] buffer = null;

    static final long serialVersionUID = -1234567890123456789L;

    /**
     *
     */
    public SerialBlob(Blob blob) throws SQLException {
        super();
        length = blob.length();
        ByteArrayOutputStream temporaryBuffer = new ByteArrayOutputStream();
        try {
            copy(blob.getBinaryStream(), temporaryBuffer, (int)length);
        } catch (IOException e) {
        }
        inStream = new ByteArrayInputStream(temporaryBuffer.toByteArray());
    }

    /**
     * @deprecated Use SerialBlob(Blob) constructor instead.
     */
    public SerialBlob(Blob blob, long length) throws SQLException {
        super();
        this.length = length;
        ByteArrayOutputStream temporaryBuffer = new ByteArrayOutputStream();
        try {
            copy(blob.getBinaryStream(), temporaryBuffer, (int)length);
        } catch (IOException e) {
        }
        inStream = new ByteArrayInputStream(temporaryBuffer.toByteArray());
    }

    /**
     * TODO It is a temporal solution to the Blob field errors
     * @param inStream
     * @param length
     * @throws IOException
     */
    /**
     *
     */
    public SerialBlob(InputStream inStream, long length) throws IOException {
        super();
        this.inStream = inStream;
        this.length = length;
    }

    /**
     *
     */
    public SerialBlob(InputStream inStream) throws IOException {
        super();
        ByteArrayOutputStream temporaryBuffer = new ByteArrayOutputStream();
        try {
            length = copy(inStream, temporaryBuffer);
        } catch (IOException e) {
        }
        this.inStream = new ByteArrayInputStream(temporaryBuffer.toByteArray());
    }

    /**
     *
     */
    public SerialBlob(byte[] bytes) {
        super();
        length = bytes.length;
        inStream = new ByteArrayInputStream(bytes);
        //buffer = bytes;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        length = copy(inStream, out);
        out.defaultWriteObject();
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream temporaryBuffer = new ByteArrayOutputStream();
        copy(in, temporaryBuffer, (int)length);
        inStream = new ByteArrayInputStream(temporaryBuffer.toByteArray());
        in.defaultReadObject();
    }

    private long copy(InputStream source, OutputStream destination) throws IOException {
        byte[] buf = new byte[4096];
        int numRead;
        int numberOfBytesCopied = 0;
        while(-1!= (numRead = source.read(buf))) {
            destination.write(buf, 0, numRead);
            destination.flush();
            numberOfBytesCopied+=numRead;
        }
        return numberOfBytesCopied;
    }

    private void copy(InputStream source, OutputStream destination, int length) throws IOException {
        byte[] buf = new byte[4096];
        int bytesRemaining = length;
        int numRead = source.read(buf, 0, Math.min(buf.length, bytesRemaining));
        while (numRead != -1 && bytesRemaining > 0) {
            bytesRemaining -= numRead;
            destination.write(buf, 0, numRead);
            destination.flush();
            numRead = source.read(buf, 0, Math.min(buf.length, bytesRemaining));
        }
    }

	/*
	private int copy(InputStream source, OutputStream destination) throws IOException {
		int numRead;
		int numberOfBytesCopied = 0;
		while(-1!= (numRead = source.read())) {
			destination.write(numRead);
			numberOfBytesCopied++;
		}
		destination.flush(  );
		return numberOfBytesCopied;
	}

	private void copy(InputStream source, OutputStream destination, int length) throws IOException {
		int counter;
		int numRead;
		for (counter = 0; counter < length; counter++) {
			numRead = source.read();
			destination.write(numRead);
		}
		destination.flush();
	}*/

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public long length() throws SQLException {
        //if (buffer != null)  length = buffer.length;
        return length;
    }

    public byte[] getBytes(long pos, int length) throws SQLException {
        ByteArrayInputStream byteStream = (ByteArrayInputStream) inStream;
        byte[] byteArray = new byte[length];
        try {
            byteStream.read(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }

    public InputStream getBinaryStream() throws SQLException {
        if (inStream == null) throw new SQLException();
        return inStream;
    }

    public long position(byte[] pattern, long start) throws SQLException {
        return 0;
    }


    public long position(Blob pattern, long start) throws SQLException {
        return position(pattern.getBytes(0, (int)pattern.length()), start);
    }

    public int setBytes(long pos, byte[] bytes, int offset, int len) throws SQLException {
        return 0;
    }

    public int setBytes(long pos,  byte[] bytes) throws SQLException {
        return 0;
    }

    /* (non-Javadoc)
     * @see java.sql.Blob#setBinaryStream(long)
     */
    public OutputStream setBinaryStream(long pos) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see java.sql.Blob#truncate(long)
     */
    public void truncate(long len) throws SQLException {
        // TODO Auto-generated method stub

    }

    public void free() throws SQLException {
        // TODO Auto-generated method stub

    }

    public InputStream getBinaryStream(long pos, long length)
            throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }


}
