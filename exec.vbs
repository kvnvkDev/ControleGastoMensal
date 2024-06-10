Set oShell = CreateObject ("Wscript.Shell") 
Dim strArgs
strArgs = "cmd /c exec.bat"
oShell.Run strArgs, 0, false