package rpg.core;

import java.io.*;

/**
 * For a given MessageRecord based by format name, this class knows how to
 * read and load detail records for that MessageRecord.
 *
 *
 * @tag Copyright (C) 2000 IBM Corp. All Rights Reserved.
 *
 * @tag Start date: (04/25/00 2:43:09 PM)
 *
 * @author
 *
 * @tag    Date          Flag        Description
 */

public class MessageReader
{
    final static String COPYRIGHT =  "Copyright (C) 2000 IBM Corp. All Rights Reserved";
    /**
     * Reads the detail records for the MessageRecord.
     *
     */

    public void readDetail(MessageRecord mrec, MessageHandler mh, MessageRouter mr)
            throws IOException
    {
        String formatname = null;
        MessageRecord detail;

        detail = mh.receiveMessage(mr);
        formatname = detail.getFormatName();

        while (!detail.isEmpty())
        {
            mrec.addDetail(detail);
            detail = mh.receiveMessage(mr);
            if (!formatname.equals(detail.getFormatName()))
                throw new IOException("Mismatch on detail record format.");
        }
    }
}
