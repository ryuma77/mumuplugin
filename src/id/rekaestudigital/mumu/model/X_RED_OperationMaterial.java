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

/** Generated Model for RED_OperationMaterial
 *  @author iDempiere (generated)
 *  @version Release 13 - $Id$ */
@org.adempiere.base.Model(table="RED_OperationMaterial")
public class X_RED_OperationMaterial extends PO implements I_RED_OperationMaterial, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20260510L;

    /** Standard Constructor */
    public X_RED_OperationMaterial (Properties ctx, int RED_OperationMaterial_ID, String trxName)
    {
      super (ctx, RED_OperationMaterial_ID, trxName);
      /** if (RED_OperationMaterial_ID == 0)
        {
			setRED_OperationMaterial_ID (0);
			setisOptional (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_RED_OperationMaterial (Properties ctx, int RED_OperationMaterial_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, RED_OperationMaterial_ID, trxName, virtualColumns);
      /** if (RED_OperationMaterial_ID == 0)
        {
			setRED_OperationMaterial_ID (0);
			setisOptional (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_RED_OperationMaterial (Properties ctx, String RED_OperationMaterial_UU, String trxName)
    {
      super (ctx, RED_OperationMaterial_UU, trxName);
      /** if (RED_OperationMaterial_UU == null)
        {
			setRED_OperationMaterial_ID (0);
			setisOptional (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_RED_OperationMaterial (Properties ctx, String RED_OperationMaterial_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, RED_OperationMaterial_UU, trxName, virtualColumns);
      /** if (RED_OperationMaterial_UU == null)
        {
			setRED_OperationMaterial_ID (0);
			setisOptional (false);
// N
        } */
    }

    /** Load Constructor */
    public X_RED_OperationMaterial (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_RED_OperationMaterial[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Deprecated(since="13") // use better methods with cache
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_ID)
			.getPO(getC_UOM_ID(), get_TrxName());
	}

	/** Set UOM.
		@param C_UOM_ID Unit of Measure
	*/
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1)
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
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

	@Deprecated(since="13") // use better methods with cache
	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_ID)
			.getPO(getM_Product_ID(), get_TrxName());
	}

	/** Set Product.
		@param M_Product_ID Product, Service, Item
	*/
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1)
			set_Value (COLUMNNAME_M_Product_ID, null);
		else
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Qty Required.
		@param QtyRequiered Qty Required
	*/
	public void setQtyRequiered (BigDecimal QtyRequiered)
	{
		set_Value (COLUMNNAME_QtyRequiered, QtyRequiered);
	}

	/** Get Qty Required.
		@return Qty Required	  */
	public BigDecimal getQtyRequiered()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyRequiered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set RED_OperationMaterial.
		@param RED_OperationMaterial_ID RED_OperationMaterial
	*/
	public void setRED_OperationMaterial_ID (int RED_OperationMaterial_ID)
	{
		if (RED_OperationMaterial_ID < 1)
			set_ValueNoCheck (COLUMNNAME_RED_OperationMaterial_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_RED_OperationMaterial_ID, Integer.valueOf(RED_OperationMaterial_ID));
	}

	/** Get RED_OperationMaterial.
		@return RED_OperationMaterial	  */
	public int getRED_OperationMaterial_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RED_OperationMaterial_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RED_OperationMaterial_UU.
		@param RED_OperationMaterial_UU RED_OperationMaterial_UU
	*/
	public void setRED_OperationMaterial_UU (String RED_OperationMaterial_UU)
	{
		set_Value (COLUMNNAME_RED_OperationMaterial_UU, RED_OperationMaterial_UU);
	}

	/** Get RED_OperationMaterial_UU.
		@return RED_OperationMaterial_UU	  */
	public String getRED_OperationMaterial_UU()
	{
		return (String)get_Value(COLUMNNAME_RED_OperationMaterial_UU);
	}

	@Deprecated(since="13") // use better methods with cache
	public I_RED_Operation getRED_Operation() throws RuntimeException
	{
		return (I_RED_Operation)MTable.get(getCtx(), I_RED_Operation.Table_ID)
			.getPO(getRED_Operation_ID(), get_TrxName());
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

	/** Set isOptional.
		@param isOptional isOptional
	*/
	public void setisOptional (boolean isOptional)
	{
		set_Value (COLUMNNAME_isOptional, Boolean.valueOf(isOptional));
	}

	/** Get isOptional.
		@return isOptional	  */
	public boolean isOptional()
	{
		Object oo = get_Value(COLUMNNAME_isOptional);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}
}