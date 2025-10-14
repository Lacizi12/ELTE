
cimletek = [5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000]

# Beolvasás
osszeg = int(input("Kérem az összeget (Ft): "))

print("Címletezve:")

# Addig megyünk, amíg van címlet a listában
while ft != 0:
    cimlet = cimletek.pop()  # mindig a legnagyobbat vesszük ki a végéről
    darab = osszeg // cimlet
    ft
    if darab > 0:
        print(f"{darab} db {cimlet} Ft = {darab * cimlet} Ft")
        osszeg -= darab * cimlet
