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

/** Generated Model for RED_ProductionOperationMaterial
 *  @author iDempiere (generated)
 *  @version Release 13 - $Id$ */
@org.adempiere.base.Model(table="RED_ProductionOperationMaterial")
public class X_RED_ProductionOperationMaterial extends PO implements I_RED_ProductionOperationMaterial, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20260514L;

    /** Standard Constructor */
    public X_RED_ProductionOperationMaterial (Properties ctx, int RED_ProductionOperationMaterial_ID, String trxName)
    {
      super (ctx, RED_ProductionOperationMaterial_ID, trxName);
      /** if (RED_ProductionOperationMaterial_ID == 0)
        {
			setIsGenerated (false);
// N
			setRED_ProductionOperationMaterial_ID (0);
			setisOptional (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_RED_ProductionOperationMaterial (Properties ctx, int RED_ProductionOperationMaterial_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, RED_ProductionOperationMaterial_ID, trxName, virtualColumns);
      /** if (RED_ProductionOperationMaterial_ID == 0)
        {
			setIsGenerated (false);
// N
			setRED_ProductionOperationMaterial_ID (0);
			setisOptional (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_RED_ProductionOperationMaterial (Properties ctx, String RED_ProductionOperationMaterial_UU, String trxName)
    {
      super (ctx, RED_ProductionOperationMaterial_UU, trxName);
      /** if (RED_ProductionOperationMaterial_UU == null)
        {
			setIsGenerated (false);
// N
			setRED_ProductionOperationMaterial_ID (0);
			setisOptional (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_RED_ProductionOperationMaterial (Properties ctx, String RED_ProductionOperationMaterial_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, RED_ProductionOperationMaterial_UU, trxName, virtualColumns);
      /** if (RED_ProductionOperationMaterial_UU == null)
        {
			setIsGenerated (false);
// N
			setRED_ProductionOperationMaterial_ID (0);
			setisOptional (false);
// N
        } */
    }

    /** Load Constructor */
    public X_RED_ProductionOperationMaterial (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_RED_ProductionOperationMaterial[")
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

	/** Set Generated.
		@param IsGenerated This Line is generated
	*/
	public void setIsGenerated (boolean IsGenerated)
	{
		set_ValueNoCheck (COLUMNNAME_IsGenerated, Boolean.valueOf(IsGenerated));
	}

	/** Get Generated.
		@return This Line is generated
	  */
	public boolean isGenerated()
	{
		Object oo = get_Value(COLUMNNAME_IsGenerated);
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

	/** BOM = BOM */
	public static final String MATERIALSOURCE_BOM = "BOM";
	/** MANUAL = MANUAL */
	public static final String MATERIALSOURCE_MANUAL = "MANUAL";
	/** OPERATION = OPERATION */
	public static final String MATERIALSOURCE_OPERATION = "OPERATION";
	/** Set MaterialSource.
		@param MaterialSource MaterialSource
	*/
	public void setMaterialSource (String MaterialSource)
	{

		set_Value (COLUMNNAME_MaterialSource, MaterialSource);
	}

	/** Get MaterialSource.
		@return MaterialSource	  */
	public String getMaterialSource()
	{
		return (String)get_Value(COLUMNNAME_MaterialSource);
	}

	@Deprecated(since="13") // use better methods with cache
	public org.eevolution.model.I_PP_Product_BOMLine getPP_Product_BOMLine() throws RuntimeException
	{
		return (org.eevolution.model.I_PP_Product_BOMLine)MTable.get(getCtx(), org.eevolution.model.I_PP_Product_BOMLine.Table_ID)
			.getPO(getPP_Product_BOMLine_ID(), get_TrxName());
	}

	/** Set BOM Line.
		@param PP_Product_BOMLine_ID BOM Line
	*/
	public void setPP_Product_BOMLine_ID (int PP_Product_BOMLine_ID)
	{
		if (PP_Product_BOMLine_ID < 1)
			set_Value (COLUMNNAME_PP_Product_BOMLine_ID, null);
		else
			set_Value (COLUMNNAME_PP_Product_BOMLine_ID, Integer.valueOf(PP_Product_BOMLine_ID));
	}

	/** Get BOM Line.
		@return BOM Line
	  */
	public int getPP_Product_BOMLine_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Product_BOMLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyConsumed.
		@param QtyConsumed QtyConsumed
	*/
	public void setQtyConsumed (BigDecimal QtyConsumed)
	{
		set_Value (COLUMNNAME_QtyConsumed, QtyConsumed);
	}

	/** Get QtyConsumed.
		@return QtyConsumed	  */
	public BigDecimal getQtyConsumed()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyConsumed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyIssued.
		@param QtyIssued QtyIssued
	*/
	public void setQtyIssued (BigDecimal QtyIssued)
	{
		set_Value (COLUMNNAME_QtyIssued, QtyIssued);
	}

	/** Get QtyIssued.
		@return QtyIssued	  */
	public BigDecimal getQtyIssued()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyIssued);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set RED_ProductionOperationMaterial.
		@param RED_ProductionOperationMaterial_ID RED_ProductionOperationMaterial
	*/
	public void setRED_ProductionOperationMaterial_ID (int RED_ProductionOperationMaterial_ID)
	{
		if (RED_ProductionOperationMaterial_ID < 1)
			set_ValueNoCheck (COLUMNNAME_RED_ProductionOperationMaterial_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_RED_ProductionOperationMaterial_ID, Integer.valueOf(RED_ProductionOperationMaterial_ID));
	}

	/** Get RED_ProductionOperationMaterial.
		@return RED_ProductionOperationMaterial	  */
	public int getRED_ProductionOperationMaterial_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RED_ProductionOperationMaterial_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RED_ProductionOperationMaterial_UU.
		@param RED_ProductionOperationMaterial_UU RED_ProductionOperationMaterial_UU
	*/
	public void setRED_ProductionOperationMaterial_UU (String RED_ProductionOperationMaterial_UU)
	{
		set_Value (COLUMNNAME_RED_ProductionOperationMaterial_UU, RED_ProductionOperationMaterial_UU);
	}

	/** Get RED_ProductionOperationMaterial_UU.
		@return RED_ProductionOperationMaterial_UU	  */
	public String getRED_ProductionOperationMaterial_UU()
	{
		return (String)get_Value(COLUMNNAME_RED_ProductionOperationMaterial_UU);
	}

	@Deprecated(since="13") // use better methods with cache
	public I_RED_ProductionOperation getRED_ProductionOperation() throws RuntimeException
	{
		return (I_RED_ProductionOperation)MTable.get(getCtx(), I_RED_ProductionOperation.Table_ID)
			.getPO(getRED_ProductionOperation_ID(), get_TrxName());
	}

	/** Set RED_ProductionOperation.
		@param RED_ProductionOperation_ID RED_ProductionOperation
	*/
	public void setRED_ProductionOperation_ID (int RED_ProductionOperation_ID)
	{
		if (RED_ProductionOperation_ID < 1)
			set_ValueNoCheck (COLUMNNAME_RED_ProductionOperation_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_RED_ProductionOperation_ID, Integer.valueOf(RED_ProductionOperation_ID));
	}

	/** Get RED_ProductionOperation.
		@return RED_ProductionOperation	  */
	public int getRED_ProductionOperation_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RED_ProductionOperation_ID);
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