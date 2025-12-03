# --- IMPORTÁLÁS ---
# Pandas: Ez a "programozható Excel". Olyan objektumokat (DataFrame) ad, amikkel táblázatokat kezelünk.
import pandas as pd 
# Numpy: Matematikai könyvtár, a Pandas erre épül (pl. gyors tömbműveletek).
import numpy as np 
# Matplotlib & Seaborn: A vizualizációs (rajzoló) könyvtárak.
import matplotlib.pyplot as plt 
import seaborn as sns 
# Folium: Interaktív térképekhez (Javascript alapú térképet generál Pythonból).
import folium 
from matplotlib import colors
# Sklearn (Scikit-learn): A legnépszerűbb klasszikus Machine Learning könyvtár.
# Itt importáljuk a konkrét modelleket (Döntési fa, Lineáris regresszió) és a segédeszközöket.
from sklearn.preprocessing import LabelEncoder
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier, plot_tree
from sklearn.metrics import accuracy_score, classification_report, confusion_matrix
from sklearn.linear_model import LinearRegression

# [cite_start]--- ADATBETÖLTÉS (1. feladat) [cite: 19] ---
# A read_csv egy DataFrame objektumot hoz létre a szöveges fájlból.
# Ez olyan, mintha beolvasnál egy fájlt és rögtön egy List<Object> struktúrába tennéd.
df = pd.read_csv('data.csv')

# [cite_start]--- ADATOK ELLENŐRZÉSE (2. feladat) [cite: 20] ---
# A head() metódus megmutatja az első 5 sort, hogy lássuk, jól sikerült-e a beolvasás.
print("Betöltött adatok eleje:")
display(df.head())

# Az info() metódus kiírja a típusokat (pl. float64, object/string).
# Java-s szemmel: itt látod, hogy melyik oszlop 'int', 'double' vagy 'String'.
print("\nAdattípusok és null-értékek ellenőrzése:")
print(df.info())
# [cite_start]--- ELŐFELDOLGOZÁS (3. feladat) [cite: 22] ---
# A 'Sampling Date' eredetileg csak szöveg (String). Át kell konvertálnunk dátum objektummá,
# hogy később tudjunk vele műveleteket végezni (pl. időrendbe tenni).
df['Sampling Date'] = pd.to_datetime(df['Sampling Date'])

# Megszámoljuk, hol hiányzik adat (null értékek).
# A .sum() összeadja a True értékeket (ahol hiány van).
print("\nHiányzó adatok száma oszloponként:")
print(df.isnull().sum())

# [cite_start]--- VIZUALIZÁCIÓ (4. feladat) [cite: 25] ---
# Létrehozunk egy "vásznat" (fig) és két "tengelyt" (axes) egymás mellett (1 sor, 2 oszlop).
fig, axes = plt.subplots(1, 2, figsize=(12, 6))

# Hisztogram: megmutatja, milyen gyakoriak az egyes értékek.
# Az 'ax=axes[0]' mondja meg, hogy a bal oldali képre rajzoljon.
# A KDE (Kernel Density Estimate) a görbe, ami az eloszlás simított vonala.
sns.histplot(data=df, x='Nitrogen (mg/L)', kde=True, ax=axes[0])
axes[0].set_title('Nitrogén eloszlása') # Cím beállítása

sns.histplot(data=df, x='Phosphorus (mg/L)', kde=True, ax=axes[1], color='green')
axes[1].set_title('Foszfor eloszlása')

# A layout igazítása, hogy ne csússzanak össze a feliratok.
plt.tight_layout()
plt.show() # Megjelenítés

# [cite_start]--- INTERAKTÍV TÉRKÉP (5. feladat) [cite: 33] ---
# Színskála előkészítése a nitrogénszint alapján.
max_N = round(df['Nitrogen (mg/L)'].max())
levels = range(int(max_N) + 1)
color_dict = dict(zip(levels, list(colors.cnames.values())[0:-1:10]))

# Térkép objektum (Map) példányosítása adott koordinátákon.
m = folium.Map(location=[39.8, -98.6], zoom_start=4)

# Végigiterálunk a DataFrame sorain (mint egy foreach ciklus Java-ban).
for idx, row in df.iterrows():
    n_val = round(row['Nitrogen (mg/L)'])
    # Megkeressük a megfelelő színt a dictionary-ből
    color_key = n_val if n_val in color_dict else max(color_dict.keys())
    
    # Minden sorhoz hozzáadunk egy kört (CircleMarker) a térképhez.
    folium.CircleMarker(
        location=[row['Geographical Location (Latitude)'], row['Geographical Location (Longitude)']],
        radius=5,
        color=color_dict.get(color_key, 'blue'),
        fill=True,
        popup=f"N: {row['Nitrogen (mg/L)']:.2f}" # Ez jelenik meg kattintásra
    ).add_to(m)

display(m) # Térkép kirajzolása

# [cite_start]--- KORRELÁCIÓ (7. feladat) [cite: 44] ---
# a) Új oszlop (feature engineering): Összeadjuk a két szennyezőanyagot.
df['N+P'] = df['Nitrogen (mg/L)'] + df['Phosphorus (mg/L)']

# b) Label Encoding: A gép csak számokkal tud számolni.
# A 'Good', 'Moderate', 'Poor' szövegeket átalakítjuk pl. 0, 1, 2 számokká.
le = LabelEncoder()
df['State of Sewage System_Encoded'] = le.fit_transform(df['State of Sewage System'])

# c) Korrelációs mátrix: Csak a szám típusú oszlopokat vesszük figyelembe.
# A korreláció azt méri, hogy két változó mennyire mozog együtt (-1 és 1 között).
numeric_df = df.select_dtypes(include=[np.number])
corr_matrix = numeric_df.corr(method='pearson')

# d) Hőtérkép (Heatmap) rajzolása a mátrixból.
plt.figure(figsize=(8, 6))
sns.heatmap(corr_matrix, annot=True, fmt=".2f", cmap='coolwarm')
plt.title('Korrelációs Hőtérkép')
plt.show()

# [cite_start]--- ADATOK ELŐKÉSZÍTÉSE MODELLEZÉSHEZ (13. feladat) [cite: 71, 75] ---
# 1. lépés: Eldobjuk a dátumot, mert az nem segít a döntésben (zaj).
df_model = df.drop(columns=['Sampling Date']) # Figyelem: az eredeti df-ben az átnevezés (8. feladat) később jön a kódban, itt még a régi nevet használjuk, ha sorrendben haladunk.

# 2. lépés: X és y szétválasztása.
# X (Features/Jellemzők): Amiből a gép tanul (Input).
# y (Target/Cél): Amit meg akarunk jósolni (Output).
# Az X-ből kivesszük a célváltozót ('SWS'/'State of Sewage System'), hogy ne legyen "csalás" (data leakage).
X = df_model.select_dtypes(include=[np.number]).drop(columns=['State of Sewage System_Encoded', 'N+P', 'Q'], errors='ignore') 
# A 'Population', 'Latitude', 'Longitude', 'Nitrogen', 'Phosphorus' maradnak az X-ben.

y = df['State of Sewage System'] # Ezt akarjuk megjósolni (Good/Moderate/Poor).

# [cite_start]--- TANÍTÓ ÉS TESZT HALMAZ SZÉTVÁLASZTÁSA (14. feladat) [cite: 78] ---
# Miért? Nem tesztelhetjük a tudását ugyanazon az adaton, amin tanult (mint ahogy vizsgán sem a gyakorlófeladatokat kapod vissza egy-az-egyben).
# 80% tanításra (train), 20% ellenőrzésre (test).
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# --- MODELL ÉPÍTÉS ÉS TANÍTÁS ---
# Példányosítjuk a Döntési Fát.
dt_model = DecisionTreeClassifier(random_state=42, max_depth=4)
# A .fit() metódussal "betanítjuk": megmutatjuk neki az X_train-t és a hozzá tartozó y_train-t.
# A modell megtanulja az összefüggéseket (pl. "ha a Nitrogén magas, akkor a rendszer állapota Poor").
dt_model.fit(X_train, y_train)

# Fa kirajzolása
plt.figure(figsize=(12, 8))
plot_tree(dt_model, feature_names=X.columns, class_names=dt_model.classes_, filled=True)
plt.show()

# [cite_start]--- KIÉRTÉKELÉS (15. feladat) [cite: 81] ---
# Most megkérjük a modellt, hogy tippeljen a TESZT adatokra (amiket eddig nem látott).
y_pred = dt_model.predict(X_test)

# Összehasonlítjuk a tippjeit (y_pred) a valósággal (y_test).
print(f"Pontosság: {accuracy_score(y_test, y_pred):.4f}") # Hány százalékban talált el?
print(confusion_matrix(y_test, y_pred)) # Melyik osztályt melyikkel keverte össze?

# [cite_start]--- LINEÁRIS REGRESSZIÓ (16. feladat) [cite: 83] ---
# Feladat: Van-e összefüggés a Népesség (Population) és a Szennyezés (N+P) között?
# X: Population (Független változó)
# y: N+P (Függő változó)

X_reg = df[['Population']] # Dupla zárójel kell, hogy DataFrame maradjon (2D), ne Series (1D).
y_reg = df['N+P']

# Ismét szétválasztjuk tanító és teszt halmazra.
X_train_reg, X_test_reg, y_train_reg, y_test_reg = train_test_split(X_reg, y_reg, test_size=0.2, random_state=42)

# Modell példányosítása és tanítása.
# A modell megpróbál egy egyenest illeszteni a pontokra (y = mx + b).
lin_reg = LinearRegression()
lin_reg.fit(X_train_reg, y_train_reg)

# [cite_start]--- VIZUALIZÁCIÓ [cite: 88] ---
plt.figure(figsize=(10, 6))
# Kirajzoljuk a valódi adatpontokat (kék pöttyök).
plt.scatter(X_test_reg, y_test_reg, color='blue', label='Valós adatok')
# Kirajzoljuk, amit a modell jósolt (piros vonal).
plt.plot(X_test_reg, lin_reg.predict(X_test_reg), color='red', linewidth=2, label='Regressziós egyenes')
plt.legend()
plt.show()

# Pontosság (R^2 score): Mennyire illeszkedik az egyenes az adatokra? (1.0 a tökéletes)
print(f"R^2 Score: {lin_reg.score(X_test_reg, y_test_reg):.4f}")