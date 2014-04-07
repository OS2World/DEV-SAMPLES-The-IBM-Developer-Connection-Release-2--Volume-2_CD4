VERSION 4.00
Begin VB.Form Form1 
   Caption         =   "Form1"
   ClientHeight    =   5025
   ClientLeft      =   1350
   ClientTop       =   1425
   ClientWidth     =   10095
   Height          =   5430
   Left            =   1290
   LinkTopic       =   "Form1"
   ScaleHeight     =   5025
   ScaleWidth      =   10095
   Top             =   1080
   Width           =   10215
   Begin VB.CommandButton cmdQuit 
      Caption         =   "Quit"
      Height          =   495
      Left            =   8640
      TabIndex        =   8
      Top             =   1680
      Width           =   1215
   End
   Begin VB.CommandButton cmdBuildBO 
      Caption         =   "Build Business Object"
      Height          =   495
      Left            =   7680
      TabIndex        =   7
      Top             =   960
      Width           =   2175
   End
   Begin VB.CommandButton cmdBuildComm 
      Caption         =   "Build CommArea"
      Height          =   495
      Left            =   7680
      TabIndex        =   6
      Top             =   240
      Width           =   2175
   End
   Begin VB.TextBox txtYears 
      Height          =   285
      Left            =   2160
      TabIndex        =   5
      Text            =   "Text1"
      Top             =   1440
      Width           =   735
   End
   Begin VB.TextBox txtDOB 
      Height          =   285
      Left            =   2160
      TabIndex        =   4
      Text            =   "Text1"
      Top             =   840
      Width           =   1335
   End
   Begin VB.TextBox txtName 
      Height          =   285
      Left            =   2160
      TabIndex        =   3
      Text            =   "Text1"
      Top             =   240
      Width           =   3135
   End
   Begin VB.Label lblCommAreaContent 
      BackColor       =   &H00FFFFFF&
      Caption         =   "Label1"
      ForeColor       =   &H00FF0000&
      Height          =   255
      Left            =   2400
      TabIndex        =   15
      Top             =   3720
      Width           =   7335
   End
   Begin VB.Label lblCommArea 
      Caption         =   "CommArea (Hexadecimal)"
      Height          =   495
      Left            =   120
      TabIndex        =   14
      Top             =   3840
      Width           =   2175
   End
   Begin VB.Label lblMsg 
      Caption         =   "Label1"
      BeginProperty Font 
         name            =   "MS Sans Serif"
         charset         =   0
         weight          =   400
         size            =   9.75
         underline       =   0   'False
         italic          =   0   'False
         strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H000000FF&
      Height          =   375
      Left            =   2400
      TabIndex        =   13
      Top             =   4320
      Width           =   7335
   End
   Begin VB.Label lblBO2Content 
      BackColor       =   &H00FFFFFF&
      Caption         =   "Label1"
      ForeColor       =   &H00FF0000&
      Height          =   255
      Left            =   2400
      TabIndex        =   12
      Top             =   3240
      Width           =   5895
   End
   Begin VB.Label lblBO1Content 
      BackColor       =   &H00FFFFFF&
      Caption         =   "Label1"
      ForeColor       =   &H00FF0000&
      Height          =   255
      Left            =   2400
      TabIndex        =   11
      Top             =   2760
      Width           =   5895
   End
   Begin VB.Label lblBO2 
      Caption         =   "Business Object 2"
      Height          =   495
      Left            =   120
      TabIndex        =   10
      Top             =   3240
      Width           =   2055
   End
   Begin VB.Label lblBO1 
      Caption         =   "Business Object 1"
      Height          =   375
      Left            =   120
      TabIndex        =   9
      Top             =   2760
      Width           =   1935
   End
   Begin VB.Label lblYears 
      Caption         =   "Years Service"
      Height          =   255
      Left            =   120
      TabIndex        =   2
      Top             =   1560
      Width           =   1815
   End
   Begin VB.Label lblDOB 
      Caption         =   "Date of Birth"
      Height          =   255
      Left            =   120
      TabIndex        =   1
      Top             =   960
      Width           =   1815
   End
   Begin VB.Label lblName 
      Caption         =   "Name"
      Height          =   255
      Left            =   120
      TabIndex        =   0
      Top             =   360
      Width           =   1695
   End
End
Attribute VB_Name = "Form1"
Attribute VB_Creatable = False
Attribute VB_Exposed = False
Private Sub cmdBuildBO_Click()
    BO2.BuildObject CommArea
    BO2Built = True
    lblBO2Content_Click
End Sub

Private Sub cmdBuildComm_Click()
Dim hexval As String
    Call BO1.BuildCclOBuf(CommArea)
    CommAreaLen = CommArea.length
    ReDim CommAreaAsByte(CommAreaLen) As Byte
    CommAreaAsByte = CommArea.Data
    CommAreaAsString = ""
    For i = 1 To CommAreaLen
        hexval = Hex(CommAreaAsByte(i - 1))
        If Len(hexval) = 1 Then hexval = "0" + hexval
        CommAreaAsString = CommAreaAsString + " " + hexval
    Next i
    CommAreaBuilt = True
    lblCommAreaContent_Click
    cmdBuildComm.Enabled = False
    cmdBuildBO.Enabled = True
    lblMsg.Caption = "Click 'Build Business Object' to build a business object from the CommArea"
    
End Sub

Private Sub cmdQuit_Click()
    Unload Form1
End Sub

Private Sub Form_Load()

'
' Create two new Business Objects
'
Set BO1 = CreateObject("CclBOS.Sample")
Set BO2 = CreateObject("CclBOS.Sample")
BO2Build = False
BO1.Name = "Your Name"
BO1.DateOfBirth = CDate("1/1/96")
BO1.YearsService = CInt("0")
'
' Create a new CICS OO buffer object
'
Set CommArea = CreateObject("Ccl.Buffer")
CommArea.SetString ""
CommAreaBuilt = False
'
' Prepare the screen
'
cmdBuildComm.Enabled = True
cmdBuildBO.Enabled = False
txtName.Text = BO1.Name
txtDOB.Text = Format(BO1.DateOfBirth, "dd/mm/yy")
txtYears.Text = CStr(BO1.YearsService)

lblBO1Content_Click
lblBO2Content_Click
lblCommAreaContent_Click
lblMsg.Caption = "Enter details, and click 'Build CommArea'"

End Sub


Private Sub lblBO1Content_Click()
    If IsObject(BO1) Then
        lblBO1Content.Caption = BO1.Name + " | " + Format(BO1.DateOfBirth, "dd mmm yyyy") + " | " + CStr(BO1.YearsService)
    End If
    lblMsg.Caption = "Enter details, and click 'Build CommArea'"
    cmdBuildComm.Enabled = True
    cmdBuildBO.Enabled = False
End Sub

Private Sub lblBO2Content_Click()
    If BO2Built = True Then
        lblBO2Content.Caption = BO2.Name + " | " + Format(BO2.DateOfBirth, "dd mmm yyyy") + " | " + CStr(BO2.YearsService)
    Else
        lblBO2Content.Caption = "Nothing"
    End If

End Sub


Private Sub lblCommAreaContent_Click()
If CommAreaBuilt = True Then
    lblCommAreaContent.Caption = CommAreaAsString
Else
    lblCommAreaContent.Caption = ""
End If

End Sub

Private Sub txtDOB_LostFocus()
    If Not IsDate(txtDOB.Text) Then
        Call MsgBox("Invalid date", , "Date of Birth")
        txtDOB.SetFocus
    Else
        BO1.DateOfBirth = CDate(txtDOB.Text)
        lblBO1Content_Click
    End If
End Sub


Private Sub txtName_LostFocus()
    BO1.Name = txtName.Text
    lblBO1Content_Click
End Sub


Private Sub txtYears_LostFocus()
    If Not IsNumeric(txtYears.Text) Then
        Call MsgBox("Must be numeric", , "Years Service")
        txtYears.SetFocus
    Else
        BO1.YearsService = CInt(txtYears.Text)
        lblBO1Content_Click
    End If
End Sub


