VERSION 4.00
Begin VB.Form Form1 
   Caption         =   "ECI OLE Server Sample"
   ClientHeight    =   4740
   ClientLeft      =   1500
   ClientTop       =   2415
   ClientWidth     =   6810
   ForeColor       =   &H000000FF&
   Height          =   5145
   Left            =   1440
   LinkTopic       =   "Form1"
   ScaleHeight     =   4740
   ScaleWidth      =   6810
   Top             =   2070
   Width           =   6930
   Begin VB.ListBox List1 
      Height          =   1590
      Left            =   240
      TabIndex        =   8
      Top             =   360
      Width           =   6015
   End
   Begin VB.CommandButton quit 
      Caption         =   "Quit"
      Height          =   495
      Left            =   5040
      TabIndex        =   6
      Top             =   2160
      Width           =   1215
   End
   Begin VB.CommandButton queryInfo 
      Caption         =   "Query Server Info"
      Enabled         =   0   'False
      Height          =   495
      Left            =   2520
      TabIndex        =   5
      Top             =   2160
      Width           =   1935
   End
   Begin VB.CommandButton queryDate 
      Caption         =   "Query Server Date"
      Enabled         =   0   'False
      Height          =   495
      Left            =   240
      TabIndex        =   4
      Top             =   2160
      Width           =   1935
   End
   Begin VB.TextBox InfoText 
      Enabled         =   0   'False
      Height          =   285
      Left            =   1920
      TabIndex        =   3
      Top             =   3600
      Width           =   4335
   End
   Begin VB.TextBox DateText 
      Enabled         =   0   'False
      Height          =   285
      Left            =   1920
      TabIndex        =   2
      Top             =   3120
      Width           =   4335
   End
   Begin VB.Label Message 
      Caption         =   "Select a CICS server"
      ForeColor       =   &H00FF0000&
      Height          =   375
      Left            =   240
      TabIndex        =   7
      Top             =   4200
      Width           =   6015
   End
   Begin VB.Label info 
      Caption         =   "Server Information"
      Height          =   255
      Left            =   240
      TabIndex        =   1
      Top             =   3720
      Width           =   1455
   End
   Begin VB.Label date 
      Caption         =   "Server Date"
      Height          =   255
      Left            =   240
      TabIndex        =   0
      Top             =   3240
      Width           =   1455
   End
End
Attribute VB_Name = "Form1"
Attribute VB_Creatable = False
Attribute VB_Exposed = False
Dim Samp As Object

Private Sub Form_Load()

   'Create Sample business object
   Set Samp = CreateObject("Ccl.Sample")

   'Populate list box with server names
   I = 1
   Do Until Len(Samp.GetServerName(I)) = 0
      List1.AddItem Samp.GetServerName(I) + "   " + Samp.GetServerDesc(I)
      I = I + 1
   Loop

End Sub

Private Sub Form_Unload(Cancel As Integer)
   Set Samp = Nothing
End Sub

Private Sub List1_Click()
   Dim ServerName As String
   
   'Extract CICS server name from list
   ServerName = List1.List(List1.ListIndex)
   ServerName = Left(ServerName, InStr(ServerName, " "))
   Message.Caption = "CICS server " + ServerName + " selected"

   'Initialise Sample business object with CICS server details
   Samp.SetServer ServerName, "SYSAD", "SYSAD"
   
   'Enable query buttons
   queryDate.Enabled = True
   queryInfo.Enabled = True
End Sub

Private Sub queryDate_Click()
   Message.Caption = "Call to CICS in progress..."
   DateText = Samp.GetDate
   Message.Caption = "Call to CICS completed"
End Sub

Private Sub queryInfo_Click()
   Message.Caption = "Call to CICS in progress..."
   InfoText.Text = ""
   InfoText.Text = Samp.GetInfo
   Message.Caption = "Call to CICS completed"
End Sub

Private Sub quit_Click()
    Unload Form1
End Sub


