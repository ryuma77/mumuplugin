/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package id.rekaestudigital.mumu.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for RED_Operation
 *  @author iDempiere (generated)
 *  @version Release 13 - $Id$ */
@org.adempiere.base.Model(table="RED_Operation")
public class X_RED_Operation extends PO implements I_RED_Operation, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20260510L;

    /** Standard Constructor */
    public X_RED_Operation (Properties ctx, int RED_Operation_ID, String trxName)
    {
      super (ctx, RED_Operation_ID, trxName);
      /** if (RED_Operation_ID == 0)
        {
			setName (null);
			setRED_Operation_ID (0);
			setisMaterialRequired (false);
// N
			setisQCRequired (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_RED_Operation (Properties ctx, int RED_Operation_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, RED_Operation_ID, trxName, virtualColumns);
      /** if (RED_Operation_ID == 0)
        {
			setName (null);
			setRED_Operation_ID (0);
			setisMaterialRequired (false);
// N
			setisQCRequired (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_RED_Operation (Properties ctx, String RED_Operation_UU, String trxName)
    {
      super (ctx, RED_Operation_UU, trxName);
      /** if (RED_Operation_UU == null)
        {
			setName (null);
			setRED_Operation_ID (0);
			setisMaterialRequired (false);
// N
			setisQCRequired (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_RED_Operation (Properties ctx, String RED_Operation_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, RED_Operation_UU, trxName, virtualColumns);
      /** if (RED_Operation_UU == null)
        {
			setName (null);
			setRED_Operation_ID (0);
			setisMaterialRequired (false);
// N
			setisQCRequired (false);
// N
        } */
    }

    /** Load Constructor */
    public X_RED_Operation (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_RED_Operation[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set Description.
		@param Description Optional short description of the record
	*/
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription()
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Comment/Help.
		@param Help Comment or Hint
	*/
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp()
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** DRYING = DRYING */
	public static final String MACHINETYPE_DRYING = "DRYING";
	/** DYEING = DYEING */
	public static final String MACHINETYPE_DYEING = "DYEING";
	/** FINISHING = FINISHING */
	public static final String MACHINETYPE_FINISHING = "FINISHING";
	/** HYDRO = HYDRO */
	public static final String MACHINETYPE_HYDRO = "HYDRO";
	/** INSPECTION = INSPECTION */
	public static final String MACHINETYPE_INSPECTION = "INSPECTION";
	/** KNITTING = KNITTING */
	public static final String MACHINETYPE_KNITTING = "KNITTING";
	/** OTHER = OTHER */
	public static final String MACHINETYPE_OTHER = "OTHER";
	/** PACKING = PACKING */
	public static final String MACHINETYPE_PACKING = "PACKING";
	/** Set MachineType.
		@param MachineType MachineType
	*/
	public void setMachineType (String MachineType)
	{

		set_Value (COLUMNNAME_MachineType, MachineType);
	}

	/** Get MachineType.
		@return MachineType	  */
	public String getMachineType()
	{
		return (String)get_Value(COLUMNNAME_MachineType);
	}

	/** Set Name.
		@param Name Alphanumeric identifier of the entity
	*/
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName()
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set RED_Operation.
		@param RED_Operation_ID RED_Operation
	*/
	public void setRED_Operation_ID (int RED_Operation_ID)
	{
		if (RED_Operation_ID < 1)
			set_ValueNoCheck (COLUMNNAME_RED_Operation_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_RED_Operation_ID, Integer.valueOf(RED_Operation_ID));
	}

	/** Get RED_Operation.
		@return RED_Operation	  */
	public int getRED_Operation_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RED_Operation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RED_Operation_UU.
		@param RED_Operation_UU RED_Operation_UU
	*/
	public void setRED_Operation_UU (String RED_Operation_UU)
	{
		set_Value (COLUMNNAME_RED_Operation_UU, RED_Operation_UU);
	}

	/** Get RED_Operation_UU.
		@return RED_Operation_UU	  */
	public String getRED_Operation_UU()
	{
		return (String)get_Value(COLUMNNAME_RED_Operation_UU);
	}

	/** Set Sequence.
		@param SeqNo Method of ordering records; lowest number comes first
	*/
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** DYEING = DYEING */
	public static final String STAGETYPE_DYEING = "DYEING";
	/** FINISHING = FINISHING */
	public static final String STAGETYPE_FINISHING = "FINISHING";
	/** KNITTING = KNITTING */
	public static final String STAGETYPE_KNITTING = "KNITTING";
	/** Set StageType.
		@param StageType StageType
	*/
	public void setStageType (String StageType)
	{

		set_Value (COLUMNNAME_StageType, StageType);
	}

	/** Get StageType.
		@return StageType	  */
	public String getStageType()
	{
		return (String)get_Value(COLUMNNAME_StageType);
	}

	/** Set Search Key.
		@param Value Search key for the record in the format required - must be unique
	*/
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue()
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set isMaterialRequired.
		@param isMaterialRequired isMaterialRequired
	*/
	public void setisMaterialRequired (boolean isMaterialRequired)
	{
		set_Value (COLUMNNAME_isMaterialRequired, Boolean.valueOf(isMaterialRequired));
	}

	/** Get isMaterialRequired.
		@return isMaterialRequired	  */
	public boolean isMaterialRequired()
	{
		Object oo = get_Value(COLUMNNAME_isMaterialRequired);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set isQCRequired.
		@param isQCRequired isQCRequired
	*/
	public void setisQCRequired (boolean isQCRequired)
	{
		set_Value (COLUMNNAME_isQCRequired, Boolean.valueOf(isQCRequired));
	}

	/** Get isQCRequired.
		@return isQCRequired	  */
	public boolean isQCRequired()
	{
		Object oo = get_Value(COLUMNNAME_isQCRequired);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}
}