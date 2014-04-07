/*
(c) Copyright IBM Corp. 1997  All rights reserved.

These sample programs are owned by International Business Machines
Corporation or one of its subsidiaries ("IBM") and are copyrighted and
licensed, not sold.

You may copy, modify, and distribute these sample programs in any
form without payment to IBM,  for any purpose including developing,
using, marketing or distributing programs that include or are
derivative works of the sample programs.

The sample programs are provided to you on an "AS IS" basis, without
warranty of any kind.  IBM HEREBY EXPRESSLY DISCLAIMS ALL WARRANTIES,
EITHER EXPRESS OR IMPLIED, INCLUDING , BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
Some jurisdictions do not allow for the exclusion or limitation of
implied warranties, so the above limitations or exclusions may not
apply to you.  IBM shall not be liable for any damages you suffer
as a result of using, modifying or distributing the sample programs
or their derivatives.

Each copy of any portion of this/these sample program(s) or any
derivative work, must include a the above copyright notice and warranty.
*/



package com.ibm.sdm.hotel ;
import java.rmi.* ;

public interface Customer extends PersistentEntity
{
	public void initialize(	long anID, String aFirstName, char aMiddleInitial, String aLastName, 
					String anAddress1, String anAddress2, String City,
					String aState, String aZip, String aPhone) throws RemoteException ;	

	public void uninitialize() throws RemoteException ;
	

	public void setId(long anId) throws RemoteException ;
	public void setCreditCardType(short type) throws RemoteException ;
	public void setFirstName(String aFirstName) throws RemoteException ;
	public void setMiddleInitial(char aMiddleInitial) throws RemoteException ;
	public void setLastName(String aLastName) throws RemoteException ;
	public void setAddress1(String anAddress1) throws RemoteException ;
	public void setAddress2(String anAddress2) throws RemoteException ;
	public void setCity(String aCity) throws RemoteException ;
	public void setState(String aState) throws RemoteException ;
	public void setZip(String aZip) throws RemoteException ;
	public void setPhone(String aPhone) throws RemoteException ;

	public long getId() throws RemoteException ;
	public short getCreditCardType() throws RemoteException ;
	public String getFirstName() throws RemoteException ;
	public char getMiddleInitial() throws RemoteException ;
	public String getLastName() throws RemoteException ;
	public String getAddress1() throws RemoteException ;
	public String getAddress2() throws RemoteException ;
	public String getCity() throws RemoteException ;
	public String getState() throws RemoteException ;
	public String getZip() throws RemoteException ;
	public String getPhone() throws RemoteException ;

	public void printSelf() throws RemoteException ;
	public String remoteToString() throws RemoteException ;
	
	
}	