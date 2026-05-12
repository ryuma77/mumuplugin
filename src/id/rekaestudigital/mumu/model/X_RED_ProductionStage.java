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

/** Generated Model for RED_ProductionStage
 *  @author iDempiere (generated)
 *  @version Release 13 - $Id$ */
@org.adempiere.base.Model(table="RED_ProductionStage")
public class X_RED_ProductionStage extends PO implements I_RED_ProductionStage, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20260510L;

    /** Standard Constructor */
    public X_RED_ProductionStage (Properties ctx, int RED_ProductionStage_ID, String trxName)
    {
      super (ctx, RED_ProductionStage_ID, trxName);
      /** if (RED_ProductionStage_ID == 0)
        {
			setName (null);
			setProcessed (false);
// N
			setProcessing (false);
// N
			setRED_ProductionStage_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_RED_ProductionStage (Properties ctx, int RED_ProductionStage_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, RED_ProductionStage_ID, trxName, virtualColumns);
      /** if (RED_ProductionStage_ID == 0)
        {
			setName (null);
			setProcessed (false);
// N
			setProcessing (false);
// N
			setRED_ProductionStage_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_RED_ProductionStage (Properties ctx, String RED_ProductionStage_UU, String trxName)
    {
      super (ctx, RED_ProductionStage_UU, trxName);
      /** if (RED_ProductionStage_UU == null)
        {
			setName (null);
			setProcessed (false);
// N
			setProcessing (false);
// N
			setRED_ProductionStage_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_RED_ProductionStage (Properties ctx, String RED_ProductionStage_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, RED_ProductionStage_UU, trxName, virtualColumns);
      /** if (RED_ProductionStage_UU == null)
        {
			setName (null);
			setProcessed (false);
// N
			setProcessing (false);
// N
			setRED_ProductionStage_ID (0);
        } */
    }

    /** Load Constructor */
    public X_RED_ProductionStage (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_RED_ProductionStage[")
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

	@Deprecated(since="13") // use better methods with cache
	public org.compiere.model.I_M_Product getInput_Product() throws RuntimeException
	{
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_ID)
			.getPO(getInput_Product_ID(), get_TrxName());
	}

	/** Set Input_Product_ID.
		@param Input_Product_ID Input_Product_ID
	*/
	public void setInput_Product_ID (int Input_Product_ID)
	{
		if (Input_Product_ID < 1)
			set_Value (COLUMNNAME_Input_Product_ID, null);
		else
			set_Value (COLUMNNAME_Input_Product_ID, Integer.valueOf(Input_Product_ID));
	}

	/** Get Input_Product_ID.
		@return Input_Product_ID	  */
	public int getInput_Product_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Input_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	public org.compiere.model.I_M_Production getM_Production() throws RuntimeException
	{
		return (org.compiere.model.I_M_Production)MTable.get(getCtx(), org.compiere.model.I_M_Production.Table_ID)
			.getPO(getM_Production_ID(), get_TrxName());
	}

	/** Set Production.
		@param M_Production_ID Plan for producing a product
	*/
	public void setM_Production_ID (int M_Production_ID)
	{
		if (M_Production_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_Production_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_Production_ID, Integer.valueOf(M_Production_ID));
	}

	/** Get Production.
		@return Plan for producing a product
	  */
	public int getM_Production_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Production_ID);
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

	@Deprecated(since="13") // use better methods with cache
	public org.compiere.model.I_M_Product getOuput_Product() throws RuntimeException
	{
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_ID)
			.getPO(getOuput_Product_ID(), get_TrxName());
	}

	/** Set Ouput_Product_ID.
		@param Ouput_Product_ID Ouput_Product_ID
	*/
	public void setOuput_Product_ID (int Ouput_Product_ID)
	{
		if (Ouput_Product_ID < 1)
			set_Value (COLUMNNAME_Ouput_Product_ID, null);
		else
			set_Value (COLUMNNAME_Ouput_Product_ID, Integer.valueOf(Ouput_Product_ID));
	}

	/** Get Ouput_Product_ID.
		@return Ouput_Product_ID	  */
	public int getOuput_Product_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ouput_Product_ID);
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

	/** Set QtyLoss.
		@param QtyLoss QtyLoss
	*/
	public void setQtyLoss (BigDecimal QtyLoss)
	{
		set_ValueNoCheck (COLUMNNAME_QtyLoss, QtyLoss);
	}

	/** Get QtyLoss.
		@return QtyLoss	  */
	public BigDecimal getQtyLoss()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyLoss);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyTargeted.
		@param QtyTargeted QtyTargeted
	*/
	public void setQtyTargeted (BigDecimal QtyTargeted)
	{
		set_ValueNoCheck (COLUMNNAME_QtyTargeted, QtyTargeted);
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
	public I_RED_ProductionPlan getRED_ProductionPlan() throws RuntimeException
	{
		return (I_RED_ProductionPlan)MTable.get(getCtx(), I_RED_ProductionPlan.Table_ID)
			.getPO(getRED_ProductionPlan_ID(), get_TrxName());
	}

	/** Set RED_ProductionPlan.
		@param RED_ProductionPlan_ID RED_ProductionPlan
	*/
	public void setRED_ProductionPlan_ID (int RED_ProductionPlan_ID)
	{
		if (RED_ProductionPlan_ID < 1)
			set_ValueNoCheck (COLUMNNAME_RED_ProductionPlan_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_RED_ProductionPlan_ID, Integer.valueOf(RED_ProductionPlan_ID));
	}

	/** Get RED_ProductionPlan.
		@return RED_ProductionPlan	  */
	public int getRED_ProductionPlan_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RED_ProductionPlan_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set RED_ProductionStage_UU.
		@param RED_ProductionStage_UU RED_ProductionStage_UU
	*/
	public void setRED_ProductionStage_UU (String RED_ProductionStage_UU)
	{
		set_Value (COLUMNNAME_RED_ProductionStage_UU, RED_ProductionStage_UU);
	}

	/** Get RED_ProductionStage_UU.
		@return RED_ProductionStage_UU	  */
	public String getRED_ProductionStage_UU()
	{
		return (String)get_Value(COLUMNNAME_RED_ProductionStage_UU);
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

	/** CANCELLED = CANCELLED */
	public static final String STATUS_CANCELLED = "CANCELLED";
	/** COMPLETED = COMPLETED */
	public static final String STATUS_COMPLETED = "COMPLETED";
	/** INPROGRESS = INPROGRESS */
	public static final String STATUS_INPROGRESS = "INPROGRESS";
	/** NOT STARTED = NOTSTARTED */
	public static final String STATUS_NOTSTARTED = "NOTSTARTED";
	/** PAUSED = PAUSED */
	public static final String STATUS_PAUSED = "PAUSED";
	/** READY = READY */
	public static final String STATUS_READY = "READY";
	/** SKIPPED = SKIPPED */
	public static final String STATUS_SKIPPED = "SKIPPED";
	/** WAITINGMACHINE = WAITINGMACHINE */
	public static final String STATUS_WAITINGMACHINE = "WAITINGMACHINE";
	/** WAITINGMATERIAL = WAITINGMATERIAL */
	public static final String STATUS_WAITINGMATERIAL = "WAITINGMATERIAL";
	/** Set Status.
		@param Status Status of the currently running check
	*/
	public void setStatus (String Status)
	{

		set_ValueNoCheck (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status of the currently running check
	  */
	public String getStatus()
	{
		return (String)get_Value(COLUMNNAME_Status);
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