
/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2002 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" and
 *    "Apache POI" must not be used to endorse or promote products
 *    derived from this software without prior written permission. For
 *    written permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    "Apache POI", nor may "Apache" appear in their name, without
 *    prior written permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

package org.apache.poi.hssf.record.formula;

import org.apache.poi.util.LittleEndian;
import org.apache.poi.hssf.util.RangeAddress;

/**
 * Title:        Area 3D Ptg <P>
 * Description:  Defined a area in Extern Sheet. <P>
 * REFERENCE:  <P>
 * @author Libin Roman (Vista Portal LDT. Developer)
 * @version 1.0-pre
 */

public class Area3DPtg extends Ptg
{
    public final static short sid  = 0x3b;
    private final static int  SIZE = 11; // 10 + 1 for Ptg
    private short             field_1_index_extern_sheet;  
    private short             field_2_first_row;
    private short             field_3_last_row;
    private short             field_4_first_column;
    private short             field_5_last_column;

    /** Creates new AreaPtg */

    public Area3DPtg()
    {
    }

    public Area3DPtg(byte[] data, int offset)
    {
        offset++;
        field_1_index_extern_sheet = LittleEndian.getShort(data, 0 + offset);
        field_2_first_row          = LittleEndian.getShort(data, 2 + offset);
        field_3_last_row           = LittleEndian.getShort(data, 4 + offset);
        field_4_first_column       = LittleEndian.getShort(data, 6 + offset);
        field_5_last_column        = LittleEndian.getShort(data, 8 + offset);
    }

    public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append("AreaPtg\n");
        buffer.append("Index to Extern Sheet = " + getExternSheetIndex()).append("\n");
        buffer.append("firstRow = " + getFirstRow()).append("\n");
        buffer.append("lastRow  = " + getLastRow()).append("\n");
        buffer.append("firstCol = " + getFirstColumn()).append("\n");
        buffer.append("lastCol  = " + getLastColumn()).append("\n");
        buffer.append("firstColRowRel= "
                      + isFirstColRowRelative()).append("\n");
        buffer.append("lastColRowRel = "
                      + isLastColRowRelative()).append("\n");
        buffer.append("firstColRel   = " + isFirstColRelative()).append("\n");
        buffer.append("lastColRel    = " + isLastColRelative()).append("\n");
        return buffer.toString();
    }

    public void writeBytes(byte [] array, int offset)
    {
        array[ 0 + offset ] = sid;
        LittleEndian.putShort(array, 1 + offset , getExternSheetIndex());
        LittleEndian.putShort(array, 3 + offset , getFirstRow());
        LittleEndian.putShort(array, 5 + offset , getLastRow());               
        LittleEndian.putShort(array, 7 + offset , getFirstColumnRaw());               
        LittleEndian.putShort(array, 9 + offset , getLastColumnRaw());               
    }

    public int getSize()
    {
        return SIZE;
    }
    
    public short getExternSheetIndex(){
        return field_1_index_extern_sheet;
    }
    
    public void setExternSheetIndex(short index){
        field_1_index_extern_sheet = index;
    }

    public short getFirstRow()
    {
        return field_2_first_row;
    }

    public void setFirstRow(short row)
    {
        field_2_first_row = row;
    }

    public short getLastRow()
    {
        return field_3_last_row;
    }

    public void setLastRow(short row)
    {
        field_3_last_row = row;
    }

    public short getFirstColumn()
    {
        return ( short ) (field_4_first_column & 0xFF);
    }

    public short getFirstColumnRaw()
    {
        return field_4_first_column;
    }

    public boolean isFirstColRowRelative()
    {
        return (((getFirstColumnRaw()) & 0x8000) == 0x8000);
    }

    public boolean isFirstColRelative()
    {
        return (((getFirstColumnRaw()) & 0x4000) == 0x4000);
    }

    public void setFirstColumn(short column)
    {
        field_4_first_column &= 0xFF00;
        field_4_first_column |= column & 0xFF; 
    }

    public void setFirstColumnRaw(short column)
    {
        field_4_first_column = column;   
    }

    public short getLastColumn()
    {
        return ( short ) (field_5_last_column & 0xFF);   
    }

    public short getLastColumnRaw()
    {
        return field_5_last_column;
    }

    public boolean isLastColRowRelative()
    {
        return (((getLastColumnRaw()) & 0x8000) == 1);
    }

    public boolean isLastColRelative()
    {
        return (((getFirstColumnRaw()) & 0x4000) == 1);
    }

    public void setLastColumn(short column)
    {
        field_5_last_column &= 0xFF00;
        field_5_last_column |= column & 0xFF; 
    }

    public void setLastColumnRaw(short column)
    {
        field_5_last_column = column;
    }
    
    public String getArea(){
        RangeAddress ra = new RangeAddress( getFirstColumn(),getFirstRow() + 1, getLastColumn(), getLastRow() + 1);   
        String result = ra.getAddress();
        
        return result;
    }
    
    public void setArea(String ref){
        RangeAddress ra = new RangeAddress(ref);
        
        String from = ra.getFromCell();
        String to   = ra.getToCell();
        
        setFirstColumn((short) (ra.getXPosition(from) -1));
        setFirstRow((short) (ra.getYPosition(from) -1));
        setLastColumn((short) (ra.getXPosition(to) -1));
        setLastRow((short) (ra.getYPosition(to) -1));
                        
    }

    public String toFormulaString()
    {
        String result = getArea();

        return result;
    }

    
}
