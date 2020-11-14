# GeographyMiniproject
----------------------------------------
Software Engeneering 2020 - Miniproject
----------------------------------------

Matthias Allemann / Roger Dörflinger

----------------------------------------

Create Country: Enter Country Name, Population, Area, Form of Government -> save
Create/add State: Select Country which shall get the new State - Enter State Name, Population, Area, Form of Government -> save

Additional Functionality:

ComboBox:
- Existing Countries can be selected from a ComboBox. After selecting a Country, the associated States can be selected from a second ComboBox

Buttons/Input Control:
- Save-Buttons only enabled if Country, Area and Population Textfield get "correct" input (number of character range, only numbers for area and population).
- Delete- and Update-Buttons only enabled if a Country or State is selected and update-Values are accepted

Safe/Open File:
- After starting the program, Countries and States are read from files and stored again after closing the program.
- Errors or gaps in saved lines are "marked" when the program starts

Style/CSS:
- Simple GUI created using CSS and JavaFX
