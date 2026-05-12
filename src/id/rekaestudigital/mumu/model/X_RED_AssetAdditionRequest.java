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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for RED_AssetAdditionRequest
 *  @author iDempiere (generated)
 *  @version Release 13 - $Id$ */
@org.adempiere.base.Model(table="RED_AssetAdditionRequest")
public class X_RED_AssetAdditionRequest extends PO implements I_RED_AssetAdditionRequest, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20260511L;

    /** Standard Constructor */
    public X_RED_AssetAdditionRequest (Properties ctx, int RED_AssetAdditionRequest_ID, String trxName)
    {
      super (ctx, RED_AssetAdditionRequest_ID, trxName);
      /** if (RED_AssetAdditionRequest_ID == 0)
        {
			setC_DocType_ID (0);
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setPosted (false);
// N
			setProcessed (false);
// N
			setProcessing (false);
// N
			setRED_AssetAdditionRequest_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_RED_AssetAdditionRequest (Properties ctx, int RED_AssetAdditionRequest_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, RED_AssetAdditionRequest_ID, trxName, virtualColumns);
      /** if (RED_AssetAdditionRequest_ID == 0)
        {
			setC_DocType_ID (0);
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setPosted (false);
// N
			setProcessed (false);
// N
			setProcessing (false);
// N
			setRED_AssetAdditionRequest_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_RED_AssetAdditionRequest (Properties ctx, String RED_AssetAdditionRequest_UU, String trxName)
    {
      super (ctx, RED_AssetAdditionRequest_UU, trxName);
      /** if (RED_AssetAdditionRequest_UU == null)
        {
			setC_DocType_ID (0);
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setPosted (false);
// N
			setProcessed (false);
// N
			setProcessing (false);
// N
			setRED_AssetAdditionRequest_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_RED_AssetAdditionRequest (Properties ctx, String RED_AssetAdditionRequest_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, RED_AssetAdditionRequest_UU, trxName, virtualColumns);
      /** if (RED_AssetAdditionRequest_UU == null)
        {
			setC_DocType_ID (0);
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setPosted (false);
// N
			setProcessed (false);
// N
			setProcessing (false);
// N
			setRED_AssetAdditionRequest_ID (0);
        } */
    }

    /** Load Constructor */
    public X_RED_AssetAdditionRequest (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_RED_AssetAdditionRequest[")
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
	public org.compiere.model.I_A_Asset_Group getA_Asset_Group() throws RuntimeException
	{
		return (org.compiere.model.I_A_Asset_Group)MTable.get(getCtx(), org.compiere.model.I_A_Asset_Group.Table_ID)
			.getPO(getA_Asset_Group_ID(), get_TrxName());
	}

	/** Set Asset Group.
		@param A_Asset_Group_ID Group of Assets
	*/
	public void setA_Asset_Group_ID (int A_Asset_Group_ID)
	{
		if (A_Asset_Group_ID < 1)
			set_ValueNoCheck (COLUMNNAME_A_Asset_Group_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_A_Asset_Group_ID, Integer.valueOf(A_Asset_Group_ID));
	}

	/** Get Asset Group.
		@return Group of Assets
	  */
	public int getA_Asset_Group_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Group_ID);
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

	/** Set Amount.
		@param Amount Amount in a defined currency
	*/
	public void setAmount (BigDecimal Amount)
	{
		set_ValueNoCheck (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AssetName.
		@param AssetName AssetName
	*/
	public void setAssetName (String AssetName)
	{
		set_Value (COLUMNNAME_AssetName, AssetName);
	}

	/** Get AssetName.
		@return AssetName	  */
	public String getAssetName()
	{
		return (String)get_Value(COLUMNNAME_AssetName);
	}

	/** Set AssetNo.
		@param AssetNo AssetNo
	*/
	public void setAssetNo (String AssetNo)
	{
		set_Value (COLUMNNAME_AssetNo, AssetNo);
	}

	/** Get AssetNo.
		@return AssetNo	  */
	public String getAssetNo()
	{
		return (String)get_Value(COLUMNNAME_AssetNo);
	}

	/** Set In Service Date.
		@param AssetServiceDate Date when Asset was put into service
	*/
	public void setAssetServiceDate (Timestamp AssetServiceDate)
	{
		set_ValueNoCheck (COLUMNNAME_AssetServiceDate, AssetServiceDate);
	}

	/** Get In Service Date.
		@return Date when Asset was put into service
	  */
	public Timestamp getAssetServiceDate()
	{
		return (Timestamp)get_Value(COLUMNNAME_AssetServiceDate);
	}

	@Deprecated(since="13") // use better methods with cache
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_ID)
			.getPO(getC_BPartner_ID(), get_TrxName());
	}

	/** Set Business Partner.
		@param C_BPartner_ID Identifies a Business Partner
	*/
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner.
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Deprecated(since="13") // use better methods with cache
	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
	{
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_ID)
			.getPO(getC_Currency_ID(), get_TrxName());
	}

	/** Set Currency.
		@param C_Currency_ID The Currency for this record
	*/
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1)
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Deprecated(since="13") // use better methods with cache
	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
	{
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_ID)
			.getPO(getC_DocType_ID(), get_TrxName());
	}

	/** Set Document Type.
		@param C_DocType_ID Document type or rules
	*/
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0)
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Deprecated(since="13") // use better methods with cache
	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
	{
		return (org.compiere.model.I_C_Invoice)MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_ID)
			.getPO(getC_Invoice_ID(), get_TrxName());
	}

	/** Set Invoice.
		@param C_Invoice_ID Invoice Identifier
	*/
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Account Date.
		@param DateAcct Accounting Date
	*/
	public void setDateAcct (Timestamp DateAcct)
	{
		set_ValueNoCheck (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	public Timestamp getDateAcct()
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAcct);
	}

	/** Set Document Date.
		@param DateDoc Date of the Document
	*/
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc()
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
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

	/** DocAction AD_Reference_ID=135 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** &lt;None&gt; = -- */
	public static final String DOCACTION_None = "--";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Reverse - Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Reverse - Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Re-activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Wait Complete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** Set Document Action.
		@param DocAction The targeted status of the document
	*/
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction()
	{
		return (String)get_Value(COLUMNNAME_DocAction);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Set Document Status.
		@param DocStatus The current status of the document
	*/
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus()
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Document No.
		@param DocumentNo Document sequence number of the document
	*/
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo()
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Error Msg.
		@param ErrorMsg Error Msg
	*/
	public void setErrorMsg (String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Error Msg.
		@return Error Msg	  */
	public String getErrorMsg()
	{
		return (String)get_Value(COLUMNNAME_ErrorMsg);
	}

	/** Set InvoiceGrandTotal.
		@param InvoiceGrandTotal InvoiceGrandTotal
	*/
	public void setInvoiceGrandTotal (BigDecimal InvoiceGrandTotal)
	{
		set_ValueNoCheck (COLUMNNAME_InvoiceGrandTotal, InvoiceGrandTotal);
	}

	/** Get InvoiceGrandTotal.
		@return InvoiceGrandTotal	  */
	public BigDecimal getInvoiceGrandTotal()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InvoiceGrandTotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Posted.
		@param Posted Posting status
	*/
	public void setPosted (boolean Posted)
	{
		set_ValueNoCheck (COLUMNNAME_Posted, Boolean.valueOf(Posted));
	}

	/** Get Posted.
		@return Posting status
	  */
	public boolean isPosted()
	{
		Object oo = get_Value(COLUMNNAME_Posted);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set PostingStatus.
		@param PostingStatus PostingStatus
	*/
	public void setPostingStatus (String PostingStatus)
	{
		set_Value (COLUMNNAME_PostingStatus, PostingStatus);
	}

	/** Get PostingStatus.
		@return PostingStatus	  */
	public String getPostingStatus()
	{
		return (String)get_Value(COLUMNNAME_PostingStatus);
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

	/** Set Processed On.
		@param ProcessedOn The date+time (expressed in decimal format) when the document has been processed
	*/
	public void setProcessedOn (BigDecimal ProcessedOn)
	{
		set_Value (COLUMNNAME_ProcessedOn, ProcessedOn);
	}

	/** Get Processed On.
		@return The date+time (expressed in decimal format) when the document has been processed
	  */
	public BigDecimal getProcessedOn()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProcessedOn);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Process Now.
		@param Processing Process Now
	*/
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing()
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
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

	/** Set RED_AssetAdditionRequest_UU.
		@param RED_AssetAdditionRequest_UU RED_AssetAdditionRequest_UU
	*/
	public void setRED_AssetAdditionRequest_UU (String RED_AssetAdditionRequest_UU)
	{
		set_Value (COLUMNNAME_RED_AssetAdditionRequest_UU, RED_AssetAdditionRequest_UU);
	}

	/** Get RED_AssetAdditionRequest_UU.
		@return RED_AssetAdditionRequest_UU	  */
	public String getRED_AssetAdditionRequest_UU()
	{
		return (String)get_Value(COLUMNNAME_RED_AssetAdditionRequest_UU);
	}
}