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

/** Generated Model for RED_ProductionOperation
 *  @author iDempiere (generated)
 *  @version Release 13 - $Id$ */
@org.adempiere.base.Model(table="RED_ProductionOperation")
public class X_RED_ProductionOperation extends PO implements I_RED_ProductionOperation, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20260514L;

    /** Standard Constructor */
    public X_RED_ProductionOperation (Properties ctx, int RED_ProductionOperation_ID, String trxName)
    {
      super (ctx, RED_ProductionOperation_ID, trxName);
      /** if (RED_ProductionOperation_ID == 0)
        {
			setName (null);
			setProcessed (false);
// N
			setProcessing (false);
// N
			setRED_ProductionOperation_ID (0);
			setisMaterialRequired (false);
// N
			setisQCRequired (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_RED_ProductionOperation (Properties ctx, int RED_ProductionOperation_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, RED_ProductionOperation_ID, trxName, virtualColumns);
      /** if (RED_ProductionOperation_ID == 0)
        {
			setName (null);
			setProcessed (false);
// N
			setProcessing (false);
// N
			setRED_ProductionOperation_ID (0);
			setisMaterialRequired (false);
// N
			setisQCRequired (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_RED_ProductionOperation (Properties ctx, String RED_ProductionOperation_UU, String trxName)
    {
      super (ctx, RED_ProductionOperation_UU, trxName);
      /** if (RED_ProductionOperation_UU == null)
        {
			setName (null);
			setProcessed (false);
// N
			setProcessing (false);
// N
			setRED_ProductionOperation_ID (0);
			setisMaterialRequired (false);
// N
			setisQCRequired (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_RED_ProductionOperation (Properties ctx, String RED_ProductionOperation_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, RED_ProductionOperation_UU, trxName, virtualColumns);
      /** if (RED_ProductionOperation_UU == null)
        {
			setName (null);
			setProcessed (false);
// N
			setProcessing (false);
// N
			setRED_ProductionOperation_ID (0);
			setisMaterialRequired (false);
// N
			setisQCRequired (false);
// N
        } */
    }

    /** Load Constructor */
    public X_RED_ProductionOperation (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_RED_ProductionOperation[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	@Deprecated(since="13") // use better methods with cache
	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
			.getPO(getAD_User_ID(), get_TrxName());
	}

	/** Set User/Contact.
		@param AD_User_ID User within the system - Internal or Business Partner Contact
	*/
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
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

	/** Set End Date.
		@param EndDate Last effective date (inclusive)
	*/
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate()
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
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

	/** Set OperationType.
		@param OperationType OperationType
	*/
	public void setOperationType (String OperationType)
	{
		set_Value (COLUMNNAME_OperationType, OperationType);
	}

	/** Get OperationType.
		@return OperationType	  */
	public String getOperationType()
	{
		return (String)get_Value(COLUMNNAME_OperationType);
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

	/** Set ProgressPercent.
		@param ProgressPercent ProgressPercent
	*/
	public void setProgressPercent (BigDecimal ProgressPercent)
	{
		set_Value (COLUMNNAME_ProgressPercent, ProgressPercent);
	}

	/** Get ProgressPercent.
		@return ProgressPercent	  */
	public BigDecimal getProgressPercent()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProgressPercent);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyActual.
		@param QtyActual QtyActual
	*/
	public void setQtyActual (BigDecimal QtyActual)
	{
		set_ValueNoCheck (COLUMNNAME_QtyActual, QtyActual);
	}

	/** Get QtyActual.
		@return QtyActual	  */
	public BigDecimal getQtyActual()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyActual);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyTargeted.
		@param QtyTargeted QtyTargeted
	*/
	public void setQtyTargeted (BigDecimal QtyTargeted)
	{
		set_Value (COLUMNNAME_QtyTargeted, QtyTargeted);
	}

	/** Get QtyTargeted.
		@return QtyTargeted	  */
	public BigDecimal getQtyTargeted()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyTargeted);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Deprecated(since="13") // use better methods with cache
	public I_RED_Machine getRED_Machine() throws RuntimeException
	{
		return (I_RED_Machine)MTable.get(getCtx(), I_RED_Machine.Table_ID)
			.getPO(getRED_Machine_ID(), get_TrxName());
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

	/** Set RED_ProductionOperation_UU.
		@param RED_ProductionOperation_UU RED_ProductionOperation_UU
	*/
	public void setRED_ProductionOperation_UU (String RED_ProductionOperation_UU)
	{
		set_Value (COLUMNNAME_RED_ProductionOperation_UU, RED_ProductionOperation_UU);
	}

	/** Get RED_ProductionOperation_UU.
		@return RED_ProductionOperation_UU	  */
	public String getRED_ProductionOperation_UU()
	{
		return (String)get_Value(COLUMNNAME_RED_ProductionOperation_UU);
	}

	@Deprecated(since="13") // use better methods with cache
	public I_RED_ProductionStage getRED_ProductionStage() throws RuntimeException
	{
		return (I_RED_ProductionStage)MTable.get(getCtx(), I_RED_ProductionStage.Table_ID)
			.getPO(getRED_ProductionStage_ID(), get_TrxName());
	}

	/** Set RED_ProductionStage.
		@param RED_ProductionStage_ID RED_ProductionStage
	*/
	public void setRED_ProductionStage_ID (int RED_ProductionStage_ID)
	{
		if (RED_ProductionStage_ID < 1)
			set_ValueNoCheck (COLUMNNAME_RED_ProductionStage_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_RED_ProductionStage_ID, Integer.valueOf(RED_ProductionStage_ID));
	}

	/** Get RED_ProductionStage.
		@return RED_ProductionStage	  */
	public int getRED_ProductionStage_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RED_ProductionStage_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** CANCELLED = CANCELLED */
	public static final String RED_PRODUCTIONSTATUS_CANCELLED = "CANCELLED";
	/** COMPLETED = COMPLETED */
	public static final String RED_PRODUCTIONSTATUS_COMPLETED = "COMPLETED";
	/** INPROGRESS = INPROGRESS */
	public static final String RED_PRODUCTIONSTATUS_INPROGRESS = "INPROGRESS";
	/** NOT STARTED = NOTSTARTED */
	public static final String RED_PRODUCTIONSTATUS_NOTSTARTED = "NOTSTARTED";
	/** PAUSED = PAUSED */
	public static final String RED_PRODUCTIONSTATUS_PAUSED = "PAUSED";
	/** READY = READY */
	public static final String RED_PRODUCTIONSTATUS_READY = "READY";
	/** SKIPPED = SKIPPED */
	public static final String RED_PRODUCTIONSTATUS_SKIPPED = "SKIPPED";
	/** WAITINGMACHINE = WAITINGMACHINE */
	public static final String RED_PRODUCTIONSTATUS_WAITINGMACHINE = "WAITINGMACHINE";
	/** WAITINGMATERIAL = WAITINGMATERIAL */
	public static final String RED_PRODUCTIONSTATUS_WAITINGMATERIAL = "WAITINGMATERIAL";
	/** Set RED_ProductionStatus.
		@param RED_ProductionStatus RED_ProductionStatus
	*/
	public void setRED_ProductionStatus (String RED_ProductionStatus)
	{

		set_ValueNoCheck (COLUMNNAME_RED_ProductionStatus, RED_ProductionStatus);
	}

	/** Get RED_ProductionStatus.
		@return RED_ProductionStatus	  */
	public String getRED_ProductionStatus()
	{
		return (String)get_Value(COLUMNNAME_RED_ProductionStatus);
	}

	/** Set Remarks.
		@param Remarks Remarks
	*/
	public void setRemarks (String Remarks)
	{
		set_Value (COLUMNNAME_Remarks, Remarks);
	}

	/** Get Remarks.
		@return Remarks	  */
	public String getRemarks()
	{
		return (String)get_Value(COLUMNNAME_Remarks);
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

	/** Set Start Date.
		@param StartDate First effective day (inclusive)
	*/
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate()
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
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