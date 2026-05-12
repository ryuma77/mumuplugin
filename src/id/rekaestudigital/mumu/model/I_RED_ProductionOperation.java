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
package id.rekaestudigital.mumu.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for RED_ProductionOperation
 *  @author iDempiere (generated) 
 *  @version Release 13
 */
@SuppressWarnings("all")
public interface I_RED_ProductionOperation 
{

    /** TableName=RED_ProductionOperation */
    public static final String Table_Name = "RED_ProductionOperation";

    /** AD_Table_ID=1000020 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Tenant.
	  * Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within tenant
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within tenant
	  */
	public int getAD_Org_ID();

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	@Deprecated(since="13") // use better methods with cache
	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name EndDate */
    public static final String COLUMNNAME_EndDate = "EndDate";

	/** Set End Date.
	  * Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate);

	/** Get End Date.
	  * Last effective date (inclusive)
	  */
	public Timestamp getEndDate();

    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Comment/Help.
	  * Comment or Hint
	  */
	public void setHelp (String Help);

	/** Get Comment/Help.
	  * Comment or Hint
	  */
	public String getHelp();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name OperationType */
    public static final String COLUMNNAME_OperationType = "OperationType";

	/** Set OperationType	  */
	public void setOperationType (String OperationType);

	/** Get OperationType	  */
	public String getOperationType();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name QtyActual */
    public static final String COLUMNNAME_QtyActual = "QtyActual";

	/** Set QtyActual	  */
	public void setQtyActual (BigDecimal QtyActual);

	/** Get QtyActual	  */
	public BigDecimal getQtyActual();

    /** Column name QtyTargeted */
    public static final String COLUMNNAME_QtyTargeted = "QtyTargeted";

	/** Set QtyTargeted	  */
	public void setQtyTargeted (BigDecimal QtyTargeted);

	/** Get QtyTargeted	  */
	public BigDecimal getQtyTargeted();

    /** Column name RED_Machine_ID */
    public static final String COLUMNNAME_RED_Machine_ID = "RED_Machine_ID";

	/** Set Machine	  */
	public void setRED_Machine_ID (int RED_Machine_ID);

	/** Get Machine	  */
	public int getRED_Machine_ID();

	@Deprecated(since="13") // use better methods with cache
	public I_RED_Machine getRED_Machine() throws RuntimeException;

    /** Column name RED_Operation_ID */
    public static final String COLUMNNAME_RED_Operation_ID = "RED_Operation_ID";

	/** Set RED_Operation	  */
	public void setRED_Operation_ID (int RED_Operation_ID);

	/** Get RED_Operation	  */
	public int getRED_Operation_ID();

	@Deprecated(since="13") // use better methods with cache
	public I_RED_Operation getRED_Operation() throws RuntimeException;

    /** Column name RED_ProductionOperation_ID */
    public static final String COLUMNNAME_RED_ProductionOperation_ID = "RED_ProductionOperation_ID";

	/** Set RED_ProductionOperation	  */
	public void setRED_ProductionOperation_ID (int RED_ProductionOperation_ID);

	/** Get RED_ProductionOperation	  */
	public int getRED_ProductionOperation_ID();

    /** Column name RED_ProductionOperation_UU */
    public static final String COLUMNNAME_RED_ProductionOperation_UU = "RED_ProductionOperation_UU";

	/** Set RED_ProductionOperation_UU	  */
	public void setRED_ProductionOperation_UU (String RED_ProductionOperation_UU);

	/** Get RED_ProductionOperation_UU	  */
	public String getRED_ProductionOperation_UU();

    /** Column name RED_ProductionStage_ID */
    public static final String COLUMNNAME_RED_ProductionStage_ID = "RED_ProductionStage_ID";

	/** Set RED_ProductionStage	  */
	public void setRED_ProductionStage_ID (int RED_ProductionStage_ID);

	/** Get RED_ProductionStage	  */
	public int getRED_ProductionStage_ID();

	@Deprecated(since="13") // use better methods with cache
	public I_RED_ProductionStage getRED_ProductionStage() throws RuntimeException;

    /** Column name Remarks */
    public static final String COLUMNNAME_Remarks = "Remarks";

	/** Set Remarks	  */
	public void setRemarks (String Remarks);

	/** Get Remarks	  */
	public String getRemarks();

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public void setSeqNo (int SeqNo);

	/** Get Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public int getSeqNo();

    /** Column name StartDate */
    public static final String COLUMNNAME_StartDate = "StartDate";

	/** Set Start Date.
	  * First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate);

	/** Get Start Date.
	  * First effective day (inclusive)
	  */
	public Timestamp getStartDate();

    /** Column name Status */
    public static final String COLUMNNAME_Status = "Status";

	/** Set Status.
	  * Status of the currently running check
	  */
	public void setStatus (String Status);

	/** Get Status.
	  * Status of the currently running check
	  */
	public String getStatus();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();

    /** Column name isMaterialRequired */
    public static final String COLUMNNAME_isMaterialRequired = "isMaterialRequired";

	/** Set isMaterialRequired	  */
	public void setisMaterialRequired (boolean isMaterialRequired);

	/** Get isMaterialRequired	  */
	public boolean isMaterialRequired();

    /** Column name isQCRequired */
    public static final String COLUMNNAME_isQCRequired = "isQCRequired";

	/** Set isQCRequired	  */
	public void setisQCRequired (boolean isQCRequired);

	/** Get isQCRequired	  */
	public boolean isQCRequired();
}
