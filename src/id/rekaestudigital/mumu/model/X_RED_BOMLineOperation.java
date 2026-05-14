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

/** Generated Model for RED_BOMLineOperation
 *  @author iDempiere (generated)
 *  @version Release 13 - $Id$ */
@org.adempiere.base.Model(table="RED_BOMLineOperation")
public class X_RED_BOMLineOperation extends PO implements I_RED_BOMLineOperation, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20260514L;

    /** Standard Constructor */
    public X_RED_BOMLineOperation (Properties ctx, int RED_BOMLineOperation_ID, String trxName)
    {
      super (ctx, RED_BOMLineOperation_ID, trxName);
      /** if (RED_BOMLineOperation_ID == 0)
        {
			setRED_BOMLineOperation_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_RED_BOMLineOperation (Properties ctx, int RED_BOMLineOperation_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, RED_BOMLineOperation_ID, trxName, virtualColumns);
      /** if (RED_BOMLineOperation_ID == 0)
        {
			setRED_BOMLineOperation_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_RED_BOMLineOperation (Properties ctx, String RED_BOMLineOperation_UU, String trxName)
    {
      super (ctx, RED_BOMLineOperation_UU, trxName);
      /** if (RED_BOMLineOperation_UU == null)
        {
			setRED_BOMLineOperation_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_RED_BOMLineOperation (Properties ctx, String RED_BOMLineOperation_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, RED_BOMLineOperation_UU, trxName, virtualColumns);
      /** if (RED_BOMLineOperation_UU == null)
        {
			setRED_BOMLineOperation_ID (0);
        } */
    }

    /** Load Constructor */
    public X_RED_BOMLineOperation (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_RED_BOMLineOperation[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AllocationType.
		@param AllocationType AllocationType
	*/
	public void setAllocationType (String AllocationType)
	{
		set_Value (COLUMNNAME_AllocationType, AllocationType);
	}

	/** Get AllocationType.
		@return AllocationType	  */
	public String getAllocationType()
	{
		return (String)get_Value(COLUMNNAME_AllocationType);
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

	/** Set PercentAllocated.
		@param PercentAllocated PercentAllocated
	*/
	public void setPercentAllocated (BigDecimal PercentAllocated)
	{
		set_Value (COLUMNNAME_PercentAllocated, PercentAllocated);
	}

	/** Get PercentAllocated.
		@return PercentAllocated	  */
	public BigDecimal getPercentAllocated()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PercentAllocated);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyAllocated.
		@param QtyAllocated QtyAllocated
	*/
	public void setQtyAllocated (BigDecimal QtyAllocated)
	{
		set_Value (COLUMNNAME_QtyAllocated, QtyAllocated);
	}

	/** Get QtyAllocated.
		@return QtyAllocated	  */
	public BigDecimal getQtyAllocated()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyAllocated);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set RED_BOMLineOperation.
		@param RED_BOMLineOperation_ID RED_BOMLineOperation
	*/
	public void setRED_BOMLineOperation_ID (int RED_BOMLineOperation_ID)
	{
		if (RED_BOMLineOperation_ID < 1)
			set_ValueNoCheck (COLUMNNAME_RED_BOMLineOperation_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_RED_BOMLineOperation_ID, Integer.valueOf(RED_BOMLineOperation_ID));
	}

	/** Get RED_BOMLineOperation.
		@return RED_BOMLineOperation	  */
	public int getRED_BOMLineOperation_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RED_BOMLineOperation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RED_BOMLineOperation_UU.
		@param RED_BOMLineOperation_UU RED_BOMLineOperation_UU
	*/
	public void setRED_BOMLineOperation_UU (String RED_BOMLineOperation_UU)
	{
		set_Value (COLUMNNAME_RED_BOMLineOperation_UU, RED_BOMLineOperation_UU);
	}

	/** Get RED_BOMLineOperation_UU.
		@return RED_BOMLineOperation_UU	  */
	public String getRED_BOMLineOperation_UU()
	{
		return (String)get_Value(COLUMNNAME_RED_BOMLineOperation_UU);
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
}