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

/** Generated Model for RED_InOutLineSerial
 *  @author iDempiere (generated)
 *  @version Release 13 - $Id$ */
@org.adempiere.base.Model(table="RED_InOutLineSerial")
public class X_RED_InOutLineSerial extends PO implements I_RED_InOutLineSerial, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20260514L;

    /** Standard Constructor */
    public X_RED_InOutLineSerial (Properties ctx, int RED_InOutLineSerial_ID, String trxName)
    {
      super (ctx, RED_InOutLineSerial_ID, trxName);
      /** if (RED_InOutLineSerial_ID == 0)
        {
			setIsAssetRegistered (false);
// N
			setProcessed (false);
// N
			setRED_InOutLineSerial_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_RED_InOutLineSerial (Properties ctx, int RED_InOutLineSerial_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, RED_InOutLineSerial_ID, trxName, virtualColumns);
      /** if (RED_InOutLineSerial_ID == 0)
        {
			setIsAssetRegistered (false);
// N
			setProcessed (false);
// N
			setRED_InOutLineSerial_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_RED_InOutLineSerial (Properties ctx, String RED_InOutLineSerial_UU, String trxName)
    {
      super (ctx, RED_InOutLineSerial_UU, trxName);
      /** if (RED_InOutLineSerial_UU == null)
        {
			setIsAssetRegistered (false);
// N
			setProcessed (false);
// N
			setRED_InOutLineSerial_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_RED_InOutLineSerial (Properties ctx, String RED_InOutLineSerial_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, RED_InOutLineSerial_UU, trxName, virtualColumns);
      /** if (RED_InOutLineSerial_UU == null)
        {
			setIsAssetRegistered (false);
// N
			setProcessed (false);
// N
			setRED_InOutLineSerial_ID (0);
        } */
    }

    /** Load Constructor */
    public X_RED_InOutLineSerial (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_RED_InOutLineSerial[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Deprecated(since="13") // use better methods with cache
	public org.compiere.model.I_A_Asset_Addition getA_Asset_Addition() throws RuntimeException
	{
		return (org.compiere.model.I_A_Asset_Addition)MTable.get(getCtx(), org.compiere.model.I_A_Asset_Addition.Table_ID)
			.getPO(getA_Asset_Addition_ID(), get_TrxName());
	}

	/** Set Asset Addition.
		@param A_Asset_Addition_ID Asset Addition
	*/
	public void setA_Asset_Addition_ID (int A_Asset_Addition_ID)
	{
		if (A_Asset_Addition_ID < 1)
			set_ValueNoCheck (COLUMNNAME_A_Asset_Addition_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_A_Asset_Addition_ID, Integer.valueOf(A_Asset_Addition_ID));
	}

	/** Get Asset Addition.
		@return Asset Addition	  */
	public int getA_Asset_Addition_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Addition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Deprecated(since="13") // use better methods with cache
	public org.compiere.model.I_A_Asset getA_Asset() throws RuntimeException
	{
		return (org.compiere.model.I_A_Asset)MTable.get(getCtx(), org.compiere.model.I_A_Asset.Table_ID)
			.getPO(getA_Asset_ID(), get_TrxName());
	}

	/** Set Asset.
		@param A_Asset_ID Asset used internally or by customers
	*/
	public void setA_Asset_ID (int A_Asset_ID)
	{
		if (A_Asset_ID < 1)
			set_Value (COLUMNNAME_A_Asset_ID, null);
		else
			set_Value (COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
	}

	/** Get Asset.
		@return Asset used internally or by customers
	  */
	public int getA_Asset_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_ID);
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

	/** Set IsAssetRegistered.
		@param IsAssetRegistered IsAssetRegistered
	*/
	public void setIsAssetRegistered (boolean IsAssetRegistered)
	{
		set_Value (COLUMNNAME_IsAssetRegistered, Boolean.valueOf(IsAssetRegistered));
	}

	/** Get IsAssetRegistered.
		@return IsAssetRegistered	  */
	public boolean isAssetRegistered()
	{
		Object oo = get_Value(COLUMNNAME_IsAssetRegistered);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	@Deprecated(since="13") // use better methods with cache
	public org.compiere.model.I_M_InOutLine getM_InOutLine() throws RuntimeException
	{
		return (org.compiere.model.I_M_InOutLine)MTable.get(getCtx(), org.compiere.model.I_M_InOutLine.Table_ID)
			.getPO(getM_InOutLine_ID(), get_TrxName());
	}

	/** Set Shipment/Receipt Line.
		@param M_InOutLine_ID Line on Shipment or Receipt document
	*/
	public void setM_InOutLine_ID (int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
	}

	/** Get Shipment/Receipt Line.
		@return Line on Shipment or Receipt document
	  */
	public int getM_InOutLine_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Deprecated(since="13") // use better methods with cache
	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException
	{
		return (org.compiere.model.I_M_InOut)MTable.get(getCtx(), org.compiere.model.I_M_InOut.Table_ID)
			.getPO(getM_InOut_ID(), get_TrxName());
	}

	/** Set Shipment/Receipt.
		@param M_InOut_ID Material Shipment Document
	*/
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Shipment/Receipt.
		@return Material Shipment Document
	  */
	public int getM_InOut_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
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

	/** Set Processed.
		@param Processed The document has been processed
	*/
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed()
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	@Deprecated(since="13") // use better methods with cache
	public I_RED_AssetAdditionRequest getRED_AssetAdditionRequest() throws RuntimeException
	{
		return (I_RED_AssetAdditionRequest)MTable.get(getCtx(), I_RED_AssetAdditionRequest.Table_ID)
			.getPO(getRED_AssetAdditionRequest_ID(), get_TrxName());
	}

	/** Set RED_AssetAdditionRequest.
		@param RED_AssetAdditionRequest_ID RED_AssetAdditionRequest
	*/
	public void setRED_AssetAdditionRequest_ID (int RED_AssetAdditionRequest_ID)
	{
		if (RED_AssetAdditionRequest_ID < 1)
			set_ValueNoCheck (COLUMNNAME_RED_AssetAdditionRequest_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_RED_AssetAdditionRequest_ID, Integer.valueOf(RED_AssetAdditionRequest_ID));
	}

	/** Get RED_AssetAdditionRequest.
		@return RED_AssetAdditionRequest	  */
	public int getRED_AssetAdditionRequest_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RED_AssetAdditionRequest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RED_InOutLineSerial.
		@param RED_InOutLineSerial_ID RED_InOutLineSerial
	*/
	public void setRED_InOutLineSerial_ID (int RED_InOutLineSerial_ID)
	{
		if (RED_InOutLineSerial_ID < 1)
			set_ValueNoCheck (COLUMNNAME_RED_InOutLineSerial_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_RED_InOutLineSerial_ID, Integer.valueOf(RED_InOutLineSerial_ID));
	}

	/** Get RED_InOutLineSerial.
		@return RED_InOutLineSerial	  */
	public int getRED_InOutLineSerial_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RED_InOutLineSerial_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RED_InOutLineSerial_UU.
		@param RED_InOutLineSerial_UU RED_InOutLineSerial_UU
	*/
	public void setRED_InOutLineSerial_UU (String RED_InOutLineSerial_UU)
	{
		set_Value (COLUMNNAME_RED_InOutLineSerial_UU, RED_InOutLineSerial_UU);
	}

	/** Get RED_InOutLineSerial_UU.
		@return RED_InOutLineSerial_UU	  */
	public String getRED_InOutLineSerial_UU()
	{
		return (String)get_Value(COLUMNNAME_RED_InOutLineSerial_UU);
	}

	/** Set Serial No.
		@param SerNo Product Serial Number 
	*/
	public void setSerNo (String SerNo)
	{
		set_ValueNoCheck (COLUMNNAME_SerNo, SerNo);
	}

	/** Get Serial No.
		@return Product Serial Number 
	  */
	public String getSerNo()
	{
		return (String)get_Value(COLUMNNAME_SerNo);
	}
}