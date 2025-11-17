# 6. gyakorlat OOP II.
print('6. gyakorlat OOP II.')


print('3.dia')
print('1.feladat: Dog osztály és leszármazottai, DogShelter osztály')

# Készíts egy Dog nevű osztályt, ami tárolja a kutya nevét, korát!
# A kor csak pozitív egész szám lehet, ha nem az, dobj ValueError kivételt!
# A kutya adatait a __str__ metódussal írasd ki!
class Dog:
    def __init__(self, name, age):
        self.name = name
        if not isinstance(age, int) or age <= 0:
            raise ValueError("A kor csak pozitív egész szám lehet!")
        self.age = age
    def __str__(self):
        return f"{self.name}, {self.age} éves"
# Definiáld a Dog osztály leszármazott osztályait: Sheepdog, GuardDog, HuntingDog!
# A breed (fajta) tulajdonságot a leszármazott osztályokban tárold!
# Tedd lehetővé, hogy a Sheepdog, GuardDog és HuntingDog osztályok kiirathatóak legyenek a print utasítással!
# Kiiratáskor jelenjen meg a nevük és a koruk, valamint az, hogy milyen típusú kutyák!

class Sheepdog(Dog):
    def __init__(self, name, age):
        super().__init__(name, age)
        self.breed = "Juhászkutya"
    def __str__(self):
        return f"{self.name}, {self.age} éves, {self.breed}"
###
###

#Készíts példányokat a három kutyafajtából, és tesztelj!        
try:
    dog1 = Sheepdog("Bodri", 5)
    #dog2 = GuardDog("Kormi", 4)
    #dog3 = HuntingDog("Morzsi", 3)
    dog4 = Dog("Rex", 2)
except ValueError as e:
    print(e)
else:
#finally:
    print(dog1)
    #print(dog2)
    #print(dog3) 
    print(dog4)


# Készíts el egy DogShelter nevű osztályt, ami tárolja a kutyákat!
# Egy DogShelter példány tárolja osztály szinten, és példány szinten is a kutyákat a nyilvántartás miatt.
# Biztosíts lehetőséget arra, hogy a len függvény egy adott példányban elhelyezett kutyák számát adja vissza!
# Hozzáadáskor bizonyosodj meg isinstance-cel, hogy valóban kutyákat tárolunk a DogShelterben!
# A kutyagyűjtemény kiiratásakor a kutyák életkora szerint növekvő sorrendben jelenjenek meg.
# Kivételekkel kezeld le az esetleges hibákat!

class DogShelter:
    overall_dogs = [] #osztályváltozó!

    def __init__(self):
        self.dogs = []

    def add_dog(self, dog):
        if not isinstance(dog, Dog):
            raise TypeError("Csak Dog típusú objektumok adhatók hozzá a menhelyhez!")
        self.dogs.append(dog)
        DogShelter.overall_dogs.append(dog) #osztályszintű lista frissítése

    def __len__(self):
        pass

    def __str__(self):
        pass

print()
shelter1 = DogShelter()
try:
    shelter1.add_dog(dog1)
    #shelter1.add_dog(dog2)
    shelter1.add_dog(dog4)
    #shelter1.add_dog("Nem kutya")

except TypeError as e:
    print(e)        
print(shelter1)
print(shelter1.__len__())

print()
shelter2 = DogShelter()
dog5 = Sheepdog("Lassie", 6)
shelter2.add_dog(dog5)
print(shelter2)
print(shelter2.__len__())
print()

# Kiíratás tesztelése, osztályszintű lista elérése
print(DogShelter.overall_dogs)

for dog in DogShelter.overall_dogs:
    print(dog)


print()
print('9.dia: Diamond problem, MRO')
class A:
    def függvény(self):
        return "A-ból"
class B(A):
    def függvény(self):
        return "B-ből"
class C(A):
    def függvény(self):
        return "C-ből"
class D(B, C):
    pass
d = D()

print(d.függvény())           # Melyik metódus fut le

print(D.__mro__)       # A szülő osztályok MRO sorrendje


print()
print('10.dia: A super() függvény alkalmazása')
class A:
    def függvény(self):
        return "A-ból"
class B(A):
    def függvény(self):
        return "B-ből " + super().függvény()
class C(A):
    def függvény(self):
        return "C-ből " + super().függvény()
class D(B, C):
    def függvény(self):
        return "D-ből " + super().függvény()
d = D()
print(d.függvény()) 


print()
print('11.dia:A super függvényre példa')
class A:
    def greet(self):
        print("Hello from A")
class B(A):
    def greet(self):
        print("Hello from B")
class C(B):
    def greet(self):
        super().greet()  # Az aktuális osztály szuperosztályára mutat
      
        #super(B, self).greet() # Explicit módon az A osztály greet metódusát hívja meg

        print("Hello from C")
c = C()
c.greet()


print()
print('12.dia: inctance variables, __dict__ metódus')
print()

class ExampleClass:
    def __init__(self, val = 1):
        self.a = val
        self.__b = val * 2
example_object = ExampleClass()
print(example_object.a)
#print(example_object.__b)

print()
class ExampleClass:
    def __init__(self, val):
        self.a = val
        self.__b = val
example_object = ExampleClass(42)
example_object.c = 33
print(example_object.__dict__)

print()

class ExampleClass:
    def  __init__(self, val = 1):
        self.a = val
    def set_b(self, val): 
        self.__b = val
example_object_1 = ExampleClass() 
example_object_2 = ExampleClass(2)
example_object_2.set_b(3)
example_object_3 = ExampleClass(4) 
example_object_3.c = 5
print(example_object_1.__dict__) 
print(example_object_2.__dict__) 
print(example_object_3.__dict__)


print()
print('13.dia privát változó kiírása kívülről, name mangling')
class ExampleClass:
    def __init__(self, val = 1):
        self.__a = val
        self.__b = val * 2
    def get_vars(self):
        return self.__a, self.__b
example_object = ExampleClass()
print(example_object.get_vars())
print(example_object.__dict__)
print(example_object._ExampleClass__a, example_object._ExampleClass__b)



print()
print('13.dia Checkpoint')
class ExampleClass:
    def __init__(self, val = 1):
        self.__a = val
        self.__b = val * 2
    def set_b(self, val):
        self.__b = val
    def get_vars(self):
        return self.__a, self.__b
example_object = ExampleClass(2)
example_object.set_b(3)
print(example_object.get_vars())
print(example_object.__dict__)


print()
print('14.dia: Class variable, számláló beállítása; osztályváltozók és dictionary-k tesztelése')
class ExampleClass:    #az új class osztályváltozójának láthatósága
    counter = 0
    def __init__(self, val = 1): 
        self.__a = val
        ExampleClass.counter += 1
example_object_1 = ExampleClass() 
example_object_2 = ExampleClass(2) 
example_object_3 = ExampleClass(4)

#example_object_1.counter += 1
print(example_object_1.__dict__, example_object_1.counter) 
print(example_object_2.__dict__, example_object_2.counter) 
print(example_object_3.__dict__, example_object_3.counter)

print('*') 

class ExampleClass:   #az új class osztályváltozója privát
    __counter = 0
    def __init__(self, val = 1): 
        self.__a = val
        ExampleClass.__counter += 1
example_object_1 = ExampleClass() 
example_object_2 = ExampleClass(2) 
example_object_3 = ExampleClass(4)

#example_object_1.counter += 1 #a counter védett: AttributeError: 'ExampleClass' object has no attribute 'counter'
print(example_object_1.__dict__, example_object_1._ExampleClass__counter) 
print(example_object_2.__dict__, example_object_2._ExampleClass__counter) 
print(example_object_3.__dict__, example_object_3._ExampleClass__counter)

print('15.dia: Osztály dictionary példa') 

class ExampleClass:     #az új class osztály-dictionary-je
    varia = 1
    def __init__(self, val): 
        self.varia = val   
        ExampleClass.varia = val
print(ExampleClass.__dict__) 
print()
example_object = ExampleClass(2) 
print(ExampleClass.__dict__) 
print()
print(example_object.__dict__)


print()
print('16.dia: Attribútum ellenőrzése, a hasattr() függvény')

class ExampleClass:
    def __init__(self, val): 
        if val % 2 != 0:
            self.a = 1 
        else:
            self.b = 1
example_object = ExampleClass(1) 
print(example_object.a)
#print(example_object.b) 

if hasattr(example_object, 'b'): 
    print(example_object.b)
else: print('Nincs ilyen attribútum')

print(example_object.a)

try:
    print(example_object.b) 
except AttributeError:
    pass


print()
class ExampleClass:      # adott nevű attribútum létezik-e 
    prop = 1
print(hasattr(ExampleClass, 'attr')) 
print(hasattr(ExampleClass, 'prop'))


print()

print('17.dia: checkpoint)')
class Classy:
    def other(self):
        print("other")
    def method(self): 
        print("method") 
        self.other()
obj = Classy() 
obj.method()



print()
class Classy:
    def method(self, par): 
        print("method:", par)
obj = Classy() 
obj.method(1) 
obj.method([2, 3]) 
obj.method("Main")



print()
class Classy:
    def visible(self): 
        print("visible")
    def __hidden(self): 
        print("hidden")
object = Classy()
object.visible()
try:
    object.__hidden()
except:
    print("failed")
object._Classy__hidden()



print()
print('17.dia: __init__ = constructor vizsgálata')
class Classy:
    def __init__(self, value): 
        self.var = value
obj_1 = Classy("object") 
print(obj_1.var)
print()



print('18.dia: __dict__, __name__, __module__ vizsgálata')
print('__dict__ vizsgálata')
class Classy:  
    varia = 1
    def __init__(self): 
        self.var = 2
    def method(self): pass
    def __hidden(self): pass
obj = Classy()
print(obj.__dict__) 
print('-------------')
print(Classy.__dict__)

print()

print('__name__ vizsgálata')
class Classy:
    pass
print(Classy.__name__)
print('--------')
obj = Classy()
print(type(obj).__name__)

print()

print('__module__ vizsgálata')
class Classy:   
    pass
print(Classy.__module__) 
obj = Classy()
print(obj.__module__)

print(__name__ == "__main__")



print()  
print('19.dia: A __bases__ vizsgálata')
class Super_A: pass
class Super_B: pass
class Sub(Super_A, Super_B): pass
def printBases(cls): 
    print('( ', end='')
    for x in cls.__bases__: 
        print(x.__name__, end=' ')
    print(')')
printBases(Super_A) 
printBases(Super_B) 
printBases(Sub)     #az eredmény tuple
print(Sub.__bases__[0].__name__)
print(Sub.__bases__[1].__name__)



print()
print('19.dia: Checkpoint')
class MyClass: pass
obj = MyClass() 
obj.a = 1
obj.b = 2
obj.i = 3
obj.ireal = 3.5
obj.integer = 4
obj.z = 5
def incIntsI(obj):
    for name in obj.__dict__.keys():
        if name.startswith('i'):
            val = getattr(obj, name) 
            if isinstance(val, int):
                setattr(obj, name, val + 1)
print(obj.__dict__) 
incIntsI(obj) 
print(obj.__dict__)



print()
print('20.dia: Checkpoint')   
class Dog:
    kennel = 0
    def __init__(self,breed):
        self.breed = breed
        Dog.kennel += 1
    def __str__(self):
        return self.breed + " says: Woof!"
class SheepDog(Dog):
    def __str__(self):
        return super().__str__() + " Don't run away, Little Lamb!"
class GuardDog(Dog):
    def __str__(self):
        return super().__str__() + " Stay where you are, Mister Intruder!"
Rocky = SheepDog("Collie")
Luna = GuardDog ("Dobermann")
Lassie = SheepDog("Rough Collie")

print(Rocky)
print(Luna)
print(issubclass(SheepDog, Dog), issubclass(SheepDog, GuardDog))
print(isinstance(Rocky, GuardDog ), isinstance(Luna, GuardDog))
print(Luna is Luna, Rocky is Luna)
print(Rocky.kennel)


print()
print('21.dia: 2.feladat: Book osztály')
class Book:
    def __init__(self, title=None, author=None, pages=None):
        if not title or not author or not pages:
            raise ValueError("Minden tulajdonság megadása kötelező!")
        if not isinstance(pages, int) or pages <= 0:
            raise ValueError("Az oldalszámnak pozitív egész számnak kell lennie!")
        self.title = title
        self.author = author
        self.pages = pages      
    def __lt__(self, other):        #less than
        if not isinstance(other, Book):
            raise Exception("Összehasonlíthatatlan adatok!")
        return self.pages < other.pages
    def __eq__(self, other):         #equal
        if not isinstance(other, Book):
            raise Exception("Összehasonlíthatatlan adatok!")
        return self.pages == other.pages
    def __gt__(self, other):        #greater than
        if not isinstance(other, Book):
            raise Exception("Összehasonlíthatatlan adatok!")
        return self.pages > other.pages
    def __str__(self):
        return f"{self.title} - {self.author} ({self.pages} oldal)"
    
book1 = Book("Harry Potter", "J.K. Rowling", 300)
book2 = Book("The Lord of the Rings", "J.R.R. Tolkien", 500)
book3 = ("The Hobbit", "J.R.R. Tolkien", 250)
book4 = Book("The Hobbit", "J.R.R. Tolkien", 250)
print(book1)
print(book1 < book2)
print(book1 == book2)
try:
    print(book2 > book3)
except Exception as e:
    print(e)


print()
print('24.dia: 3.feladat: Library osztály')
class Library:
    overall_books = []             #osztályváltozó
    def __init__(self):
        self.books = []
    def add_book(self, book): 
        pass
    def __len__(self):
        pass
    def __str__(self):
        pass
