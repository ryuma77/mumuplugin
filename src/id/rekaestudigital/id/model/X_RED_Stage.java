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
package id.rekaestudigital.id.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for RED_Stage
 *  @author iDempiere (generated)
 *  @version Release 13 - $Id$ */
@org.adempiere.base.Model(table="RED_Stage")
public class X_RED_Stage extends PO implements I_RED_Stage, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20260514L;

    /** Standard Constructor */
    public X_RED_Stage (Properties ctx, int RED_Stage_ID, String trxName)
    {
      super (ctx, RED_Stage_ID, trxName);
      /** if (RED_Stage_ID == 0)
        {
			setIsDefault (false);
// N
			setIsMandatory (false);
// N
			setName (null);
			setRED_Stage_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_RED_Stage (Properties ctx, int RED_Stage_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, RED_Stage_ID, trxName, virtualColumns);
      /** if (RED_Stage_ID == 0)
        {
			setIsDefault (false);
// N
			setIsMandatory (false);
// N
			setName (null);
			setRED_Stage_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_RED_Stage (Properties ctx, String RED_Stage_UU, String trxName)
    {
      super (ctx, RED_Stage_UU, trxName);
      /** if (RED_Stage_UU == null)
        {
			setIsDefault (false);
// N
			setIsMandatory (false);
// N
			setName (null);
			setRED_Stage_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_RED_Stage (Properties ctx, String RED_Stage_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, RED_Stage_UU, trxName, virtualColumns);
      /** if (RED_Stage_UU == null)
        {
			setIsDefault (false);
// N
			setIsMandatory (false);
// N
			setName (null);
			setRED_Stage_ID (0);
        } */
    }

    /** Load Constructor */
    public X_RED_Stage (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_RED_Stage[")
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

	/** Set Default.
		@param IsDefault Default value
	*/
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Default.
		@return Default value
	  */
	public boolean isDefault()
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Mandatory.
		@param IsMandatory Data entry is required in this column
	*/
	public void setIsMandatory (boolean IsMandatory)
	{
		set_Value (COLUMNNAME_IsMandatory, Boolean.valueOf(IsMandatory));
	}

	/** Get Mandatory.
		@return Data entry is required in this column
	  */
	public boolean isMandatory()
	{
		Object oo = get_Value(COLUMNNAME_IsMandatory);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
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

	/** Set RED_Stage.
		@param RED_Stage_ID RED_Stage
	*/
	public void setRED_Stage_ID (int RED_Stage_ID)
	{
		if (RED_Stage_ID < 1)
			set_ValueNoCheck (COLUMNNAME_RED_Stage_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_RED_Stage_ID, Integer.valueOf(RED_Stage_ID));
	}

	/** Get RED_Stage.
		@return RED_Stage	  */
	public int getRED_Stage_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RED_Stage_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RED_Stage_UU.
		@param RED_Stage_UU RED_Stage_UU
	*/
	public void setRED_Stage_UU (String RED_Stage_UU)
	{
		set_Value (COLUMNNAME_RED_Stage_UU, RED_Stage_UU);
	}

	/** Get RED_Stage_UU.
		@return RED_Stage_UU	  */
	public String getRED_Stage_UU()
	{
		return (String)get_Value(COLUMNNAME_RED_Stage_UU);
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
}