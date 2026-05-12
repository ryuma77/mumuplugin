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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for RED_Machine
 *  @author iDempiere (generated)
 *  @version Release 13 - $Id$ */
@org.adempiere.base.Model(table="RED_Machine")
public class X_RED_Machine extends PO implements I_RED_Machine, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20260510L;

    /** Standard Constructor */
    public X_RED_Machine (Properties ctx, int RED_Machine_ID, String trxName)
    {
      super (ctx, RED_Machine_ID, trxName);
      /** if (RED_Machine_ID == 0)
        {
			setIsAvailable (true);
// Y
			setName (null);
			setRED_Machine_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_RED_Machine (Properties ctx, int RED_Machine_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, RED_Machine_ID, trxName, virtualColumns);
      /** if (RED_Machine_ID == 0)
        {
			setIsAvailable (true);
// Y
			setName (null);
			setRED_Machine_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_RED_Machine (Properties ctx, String RED_Machine_UU, String trxName)
    {
      super (ctx, RED_Machine_UU, trxName);
      /** if (RED_Machine_UU == null)
        {
			setIsAvailable (true);
// Y
			setName (null);
			setRED_Machine_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_RED_Machine (Properties ctx, String RED_Machine_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, RED_Machine_UU, trxName, virtualColumns);
      /** if (RED_Machine_UU == null)
        {
			setIsAvailable (true);
// Y
			setName (null);
			setRED_Machine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_RED_Machine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_RED_Machine[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	@Deprecated(since="13") // use better methods with cache
	public org.compiere.model.I_C_UOM getC_CapacityUOM() throws RuntimeException
	{
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_ID)
			.getPO(getC_CapacityUOM_ID(), get_TrxName());
	}

	/** Set C_CapacityUOM_ID.
		@param C_CapacityUOM_ID C_CapacityUOM_ID
	*/
	public void setC_CapacityUOM_ID (int C_CapacityUOM_ID)
	{
		if (C_CapacityUOM_ID < 1)
			set_Value (COLUMNNAME_C_CapacityUOM_ID, null);
		else
			set_Value (COLUMNNAME_C_CapacityUOM_ID, Integer.valueOf(C_CapacityUOM_ID));
	}

	/** Get C_CapacityUOM_ID.
		@return C_CapacityUOM_ID	  */
	public int getC_CapacityUOM_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CapacityUOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Available.
		@param IsAvailable Resource is available
	*/
	public void setIsAvailable (boolean IsAvailable)
	{
		set_Value (COLUMNNAME_IsAvailable, Boolean.valueOf(IsAvailable));
	}

	/** Get Available.
		@return Resource is available
	  */
	public boolean isAvailable()
	{
		Object oo = get_Value(COLUMNNAME_IsAvailable);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	@Deprecated(since="13") // use better methods with cache
	public I_M_Locator getM_Locator() throws RuntimeException
	{
		return (I_M_Locator)MTable.get(getCtx(), I_M_Locator.Table_ID)
			.getPO(getM_Locator_ID(), get_TrxName());
	}

	/** Set Locator.
		@param M_Locator_ID Warehouse Locator
	*/
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Locator.
		@return Warehouse Locator
	  */
	public int getM_Locator_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Deprecated(since="13") // use better methods with cache
	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
	{
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_ID)
			.getPO(getM_Warehouse_ID(), get_TrxName());
	}

	/** Set Warehouse.
		@param M_Warehouse_ID Storage Warehouse and Service Point
	*/
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1)
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Machine.
		@param RED_Machine_ID Machine
	*/
	public void setRED_Machine_ID (int RED_Machine_ID)
	{
		if (RED_Machine_ID < 1)
			set_ValueNoCheck (COLUMNNAME_RED_Machine_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_RED_Machine_ID, Integer.valueOf(RED_Machine_ID));
	}

	/** Get Machine.
		@return Machine	  */
	public int getRED_Machine_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RED_Machine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RED_Machine_UU.
		@param RED_Machine_UU RED_Machine_UU
	*/
	public void setRED_Machine_UU (String RED_Machine_UU)
	{
		set_Value (COLUMNNAME_RED_Machine_UU, RED_Machine_UU);
	}

	/** Get RED_Machine_UU.
		@return RED_Machine_UU	  */
	public String getRED_Machine_UU()
	{
		return (String)get_Value(COLUMNNAME_RED_Machine_UU);
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

	/** Set capacity.
		@param capacity capacity
	*/
	public void setcapacity (BigDecimal capacity)
	{
		set_Value (COLUMNNAME_capacity, capacity);
	}

	/** Get capacity.
		@return capacity	  */
	public BigDecimal getcapacity()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_capacity);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}