print('5. gyakorlat - Hibakezelés, osztályok bevezetés')

print()
print('3.dia:Hibakezelési példák')
try: 
    i = int("Hello!")
except Exception as e:
    print(e)
    print(e.__str__())
try: 
    print(1/0)
except Exception as e:
    print(e)
    print(e.__str__())


print()
print('4.dia: Exception tree példa\n')
def print_exception_tree(thisclass, nest = 0):
    if nest > 1:
        print(" |"*(nest-1),end="")
    if nest > 0:
        print(" +------",end="")
        print(thisclass.__name__)
    for subclass in thisclass.__subclasses__():
        print_exception_tree(subclass, nest + 1)

#print_exception_tree(BaseException)
print_exception_tree(Exception,1)   # uaz
#print_exception_tree(ArithmeticError,1)


print()
print('5.dia:A raise utasítás')
def próba(n):
    try:
        return 1 / n
    except ArithmeticError:
        print("Aritmetikai probléma!")
    return None
próba(0)               #a hiba kiváltása
print("vége")

print()
def próba(n):
    raise ZeroDivisionError
try:
    próba(2)	    #nem okozna hibát
except ArithmeticError:
    print("Mi történt? Egy hiba?")
print("vége")


print()
print('6.dia: Példa a raise utasításra')
def próba(n):
    try:
        return n / 0
    except:
        print("Megint egy hiba!")
        #raise
try:
    próba(0)
except ArithmeticError:
    print("Igen, ez egy aritmetikai hiba.")
print("vége")


print()
print('7.dia: Az assert kifejezés példái')
import math
x = float(input("Kérek egy számot: "))
assert x >= 0.0
x = math.sqrt(x)
print(x)

print()
while True:
    x = float(input("Kérek egy számot: "))
    try:
        assert x >= 0.0
        break
    except AssertionError:
        print("A szám negatív!")
x = math.sqrt(x)
print(x)


print()
print('12.dia: Checkpoint')
try: print(1/0)
except ZeroDivisionError: print("zero")
except ArithmeticError: print("arith")
except: print("end")

try: print(1/0)
except ArithmeticError: print("arith")
except ZeroDivisionError: print("zero")
except: print("end")

def rec(x):        
    assert x 
    return 1/x
try: print(rec(0))
except ZeroDivisionError: print("zero")
except: print("end")


print()
print('13.dia: 1.feladat: beolvasás hibakezelése')
def read_int(prompt, min, max):
    ###
    return
v = read_int("Kérek egy egész számot -10 és 10 között: ", -10, 10)
print("A szám:", v)

print()
print('14.dia:Verem műveletek, példák')
print('Procedurális verem')
stack = []
def push(val): stack.append(val)
def pop():
    val = stack[-1] 
    del stack[-1] 
    return val
push(3) 
push(2) 
push(1)
print(pop())
print(pop())
print(pop())


print()
print('15.dia: OOP verem')
class Stack:
    def __init__(self): 
        self.__stack_list = []
    def push(self, val):
        self.__stack_list.append(val)
    def pop(self):
        val = self.__stack_list[-1] 
        del self.__stack_list[-1] 
        return val
stack_object = Stack()
stack_object.push(3)
stack_object.push(2)
stack_object.push(1)
print(stack_object.pop())
print(stack_object.pop())
print(stack_object.pop())


print()
print('16.dia: checkpoint')
class Stack:
    def __init__(self): 
        self.__stack_list = []
    def push(self, val):
        self.__stack_list.append(val)
    def pop(self):
        val = self.__stack_list[-1] 
        del self.__stack_list[-1] 
        return val
stack1 = Stack() 
stack2 = Stack() 
stack3 = Stack()
stack1.push(1) 
stack2.push(stack1.pop() + 1)
stack3.push(stack2.pop() - 2)
print(stack3.pop())
#print(stack1.pop())      # IndexError: list index out of range (val = self.__stack_list[-1])
#print(stack2.pop())      # verem.is_empty()-t kellene használni, ami True vagy False értéket ad vissza

print()
class Auto:
    def __init__(self):
        self.__motor = "V8"        # privát attribútum
    def __motor_check(self):       # privát metódus
        return "Motor OK"  
    def inditas(self):
        if self.__motor_check():   # belső használat
            print("Indítás...")
auto = Auto()
# print(auto.__motor)      # AttributeError - nem elérhető!
# auto.__motor_check()     # AttributeError - nem elérhető!
auto.inditas()             # Ez működik


print() 
print('17.dia: Subclass - issubclass() függvény')
class Stack:
    pass
class Stack_1(Stack):
    pass
obj = Stack()
obj_1 = Stack_1()
print(issubclass(Stack_1, Stack))
print(Stack_1.__bases__[0].__name__)  # Közvetlen szülő 
print(isinstance(obj_1, Stack_1))
print(isinstance(obj_1, Stack))
print(isinstance(obj, Stack_1))

print()
print('Több szülőosztály példa')
class A:
    pass
class B:
    pass
class C(A, B):  # Két szülőosztály, a sorrend számít
    pass
print(C.__bases__)        # (<class '__main__.A'>, <class '__main__.B'>)
print(C.__bases__[0])     # <class '__main__.A'>
print(C.__bases__[1])     # <class '__main__.B'>


print()
print('18.dia: AddingStack class ráépítése a Stack class-ra, inheritance ')
class Stack:
    def __init__(self): 
        self.__stack_list = []
    def push(self, val):
        self.__stack_list.append(val) 
    def pop(self):
        val = self.__stack_list[-1] 
        del self.__stack_list[-1] 
        return val
class AddingStack(Stack): 
    def __init__(self):
        Stack.__init__(self)    # Létrehozza az adatszerkezetet a szülőosztály alapján
        self.__sum = 0          # definiálja egy saját objektumváltozót, ami védett
    def get_sum(self): 
        return self.__sum
    def push(self, val): 
        self.__sum += val 
        Stack.push(self, val)   # örökölt művelet meghívása
    def pop(self):
        val = Stack.pop(self) 
        self.__sum -= val 
        return val
stack_object = AddingStack()
for i in range(5): 
    stack_object.push(i)
    print(i,stack_object.get_sum())
print()
print('Sorban kivéve az elemeket a veremből: elem, összeg')
for i in range(5):
    print(stack_object.pop(), stack_object.get_sum())


print()
print('19.dia: 2.feladat CountingStack: a pop metódusok számát számolja')
class Stack:
    def __init__(self):
        self.__stl = []
    def push(self, val):
        self.__stl.append(val)
    def pop(self):
        val = self.__stl[-1] 
        del self.__stl[-1] 
        return val
    
class CountingStack(Stack):   
    def __init__(self):         
        Stack.__init__(self)
        self.__counter = 0
    def get_counter(self):     
        return self.__counter
    def pop(self):              
        val = Stack.pop(self)
        self.__counter += 1
        return val
        
c_stk = CountingStack() 
for i in range(100):
    c_stk.push(i) 
    c_stk.pop() 
print(c_stk.get_counter())


print() 
print('20.dia: 3.feladat A sor osztálya = Queue class')
class Queue:
    def __init__(self): 
        pass
    def put(self, elem):
        pass
    def get(self): 
        pass
que = Queue() 
que.put(1) 
que.put("dog") 
que.put(False) 
try:
    for i in range(4):
        print(que.get()) 
except:
    print("Queue error")


print()
print('21.dia: Példa egyedi kivétel osztály készítésére')
class InvalidAgeError(Exception):
    '''Exception, ha a kor nincs az érvényes tartományban'''
    def __init__(self, age, message="Az életkornak a 18 és 40 közötti tartományban kell lenni!"):
        self.age = age
        self.message = message
        super().__init__(self.message) 
        #Ez biztosítja, hogy a kivétel üzenete megfelelően legyen inicializálva az Exception osztályban.
class Student:
    def __init__(self, name, age):
        self.name = name
        self.set_age(age)
    def set_age(self, age):
        if age < 18 or age > 40:
            raise InvalidAgeError(age)
        self.age = age
    def __str__(self):
        return f"Hallgató neve: {self.name}, Kor: {self.age} év"
try:
    #student = Student("Nagy Béla", 50)
    student = Student("Kiss Anna", 18)
except InvalidAgeError as e:
    print(f"Error: {e}")
else: print(student)


print()
print('22.dia: Házi feladat, angol változónevekkel')
import math
class Point:
    def __init__(self, x=0.0, y=0.0): 
        pass
    def get_x(self):
        pass
    def get_y(self): 
        pass
    def distance_from_xy(self, x, y): 
        pass
    def distance_from_point(self, point): 
        pass
point1 = Point(0, 0) 
point2 = Point(1, 1)
print(point1.distance_from_point(point2))
print(point2.distance_from_xy(2, 0))
