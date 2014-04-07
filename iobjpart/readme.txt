Example of Using an IObjectWindow as a Part
4/3/96

This example shows how to use IObjectWindow and to create a part out of it.
It shows how to receive messages off of the message queue and to process them,
and if necessary send out part notifications.

The example is composed of two parts, MyPart and TestMyPart. MyPart is the
actual IObjectWindow subclass part. TestMyPart is the driver to show how
MyPart works.

