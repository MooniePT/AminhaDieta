; Script generated for A Minha Dieta
; Requires Inno Setup to compile

#define MyAppName "A Minha Dieta"
#define MyAppVersion "1.0.0"
#define MyAppPublisher "Equipa Tecnica"
#define MyAppJarName "AMinhaDieta-1.0.0.jar"

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{C6D2CA38-0E71-4682-8951-4045431688B7}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
AppPublisher={#MyAppPublisher}
DefaultDirName={autopf}\{#MyAppName}
DefaultGroupName={#MyAppName}
DisableProgramGroupPage=yes
; Uncomment the following line to run in non administrative install mode (install for current user only.)
;PrivilegesRequired=lowest
OutputDir=.
OutputBaseFilename=AMinhaDieta_Setup
Compression=lzma
SolidCompression=yes
WizardStyle=modern
; If you convert your src/main/resources/images/icon.png to .ico, you can uncomment this:
SetupIconFile=src\main\resources\images\icon.ico

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"
Name: "portuguese"; MessagesFile: "compiler:Languages\Portuguese.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
; IMPORTANT: Run 'mvn package' before compiling this script to generate the JAR file
Source: "target\{#MyAppJarName}"; DestDir: "{app}"; Flags: ignoreversion
Source: "src\main\resources\images\icon.ico"; DestDir: "{app}"; Flags: ignoreversion
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
; Creates a shortcut that runs the JAR using javaw.exe (requires Java on client machine)
Name: "{group}\{#MyAppName}"; Filename: "javaw.exe"; Parameters: "-jar ""{app}\{#MyAppJarName}"""; WorkingDir: "{app}"; IconFilename: "{app}\icon.ico"
Name: "{group}\{cm:UninstallProgram,{#MyAppName}}"; Filename: "{uninstallexe}"
Name: "{commondesktop}\{#MyAppName}"; Filename: "javaw.exe"; Parameters: "-jar ""{app}\{#MyAppJarName}"""; WorkingDir: "{app}"; Tasks: desktopicon; IconFilename: "{app}\icon.ico"

[Run]
Filename: "javaw.exe"; Parameters: "-jar ""{app}\{#MyAppJarName}"""; Description: "{cm:LaunchProgram,{#MyAppName}}"; Flags: nowait postinstall skipifsilent

[Code]
function InitializeSetup: Boolean;
var
  ErrorCode: Integer;
  JavaInstalled: Boolean;
  Result1: Boolean;
begin
  Result := True;
  JavaInstalled := False;

  // Check for Java in Registry (checking for standard JRE keys)
  if RegKeyExists(HKLM64, 'SOFTWARE\JavaSoft\Java Runtime Environment') or
     RegKeyExists(HKLM32, 'SOFTWARE\JavaSoft\Java Runtime Environment') or
     RegKeyExists(HKLM64, 'SOFTWARE\JavaSoft\JDK') or
     RegKeyExists(HKLM32, 'SOFTWARE\JavaSoft\JDK') then
  begin
    JavaInstalled := True;
  end;

  // Simple check: try to run java -version (hidden) logic is hard in Inno Setup without external dlls sometimes,
  // so registry check is often preferred. 
  
  // If not found in registry, we might want to ask the user
  if not JavaInstalled then
  begin
    Result1 := MsgBox('This application requires Java to run. Please download and install Java (JRE or JDK) 21 or higher.' + #13#10 +
      'Do you want to open the Java download page now?', mbConfirmation, MB_YESNO) = idYes;
    if Result1 then
    begin
      ShellExec('open', 'https://www.oracle.com/java/technologies/downloads/', '', '', SW_SHOW, ewNoWait, ErrorCode);
    end;
    // We allow installation to proceed but warn? Or block?
    // User requested "verificar se tem as ferramentas", usually implies blocking or warning.
    // Let's warn but allow proceed (maybe they have a portable JDK).
    MsgBox('Java was not detected in the standard registry keys. If you have a portable Java version, you can proceed. Otherwise, the application may not launch.', mbInformation, MB_OK);
  end;
end;
