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
import java.io.* ;

public class PersistentEntityImpl extends java.rmi.server.UnicastRemoteObject
		implements PersistentEntity, java.io.Serializable
{
	public PersistentEntityImpl() throws java.rmi.RemoteException, RemoteException
	{
	
	}

	public void setAccessMode(short theAccessMode) throws RemoteException
	{
		accessMode = theAccessMode ;
	}

           		
	public void setLocationHandle(java.lang.String theLocationHandle) throws RemoteException
	{
		locationHandle = theLocationHandle ;
	}
  
	public void setHandle(long theHandle) throws RemoteException
	{
		handle = theHandle ;  //: uniqueID
	}

	public long getHandle() throws RemoteException 
	{
		return handle ;
	}

/*

	public void notifyObservers( ) throws RemoteException 
	{
	}

	public void update() throws RemoteException 
	{
	}

	public void setDirty() throws RemoteException 
	{
	}

	public boolean isDirty() throws RemoteException 
	{
		return false ;
	}
*/


	//: Transaction management
	//: Check for locks, here
	//: Commit and rollback controlled by object server



	//: Other considerations
	//:: state values such as MODIFIED, UNMODIFIED, NEW, DELETED, LOCKED, UNLOCKED
	//:: state check methods
	//:: lock and lock monitor methods

	private short accessMode ;
	private java.lang.String locationHandle ;
	private long handle ;
}
  					