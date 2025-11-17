# routers/routes.py

from fastapi import APIRouter, HTTPException, Depends, Header
from fastapi.responses import JSONResponse
from typing import List

from schemas.schema import User, Basket, Item

from data import filehandler
from data import filereader

routers = APIRouter()

def get_admin_token() -> str:
    """Beolvassa a titkos admin tokent a fájlból."""
    try:
        with open("secret.token", "r", encoding="utf-8") as f:
            return f.read().strip()
    except FileNotFoundError:
        raise RuntimeError("KRITIKUS HIBA: secret.token fájl nem található!")
async def check_admin_token(x_token: str = Header(..., description="Adminisztrátori API token")):
    """
    Ellenőrzi, hogy a kérésben küldött 'x-token' megegyezik-e
    a szerveren tárolt tokennel.
    """
    admin_token = get_admin_token()
    if x_token != admin_token:
        raise HTTPException(status_code=403, detail="Forbidden: Érvénytelen admin token.")

@routers.post('/additem', response_model=Basket)
def additem(userid: int, item: Item) -> Basket:
    """
    Terméket ad a felhasználó kosarához. Ha a termék már létezik,
    a mennyiséget növeli. [cite: 73]
    """
    try:
        updated_basket = filehandler.add_item_to_basket(userid, item)
        return updated_basket
    except ValueError as e:
        raise HTTPException(status_code=404, detail=str(e))

@routers.put('/updateitem', response_model=Basket)
def updateitem(userid: int, itemid: int, updateItem: Item) -> Basket:
    """
    Módosítja egy adott termék attribútumait a kosárban. [cite: 74]
    """
    try:
        updated_basket = filehandler.update_item_in_basket(userid, itemid, updateItem)
        return updated_basket
    except ValueError as e:
        raise HTTPException(status_code=404, detail=str(e))

@routers.delete('/deleteitem', response_model=Basket)
def deleteitem(userid: int, itemid: int) -> Basket:
    """Töröl egy terméket az adott felhasználó kosarából. [cite: 75]"""
    try:
        updated_basket = filehandler.delete_item_from_basket(userid, itemid)
        return updated_basket
    except ValueError as e:
        raise HTTPException(status_code=404, detail=str(e))

@routers.get('/shoppingbag', response_model=List[Item])
def shoppingbag(userid: int) -> List[Item]:
    """Egy adott felhasználó kosarának tartalmát (termékeit) adja vissza. [cite: 79]"""
    basket = filereader.get_basket_by_user_id(userid)
    if not basket:
        raise HTTPException(status_code=404, detail=f"Kosár nem található a {userid} felhasználóhoz.")
    
    return basket.items

@routers.get('/getusertotal', response_model=float)
def getusertotal(userid: int) -> float:
    """Egy adott felhasználó kosarában lévő termékek összértékét adja vissza. [cite: 80]"""
    basket = filereader.get_basket_by_user_id(userid)
    if not basket:
        raise HTTPException(status_code=404, detail=f"Kosár nem található a {userid} felhasználóhoz.")
    
    total_price = sum(item.price * item.quantity for item in basket.items)
    return total_price


@routers.post('/adduser', response_model=User, dependencies=[Depends(check_admin_token)])
def adduser(user: User) -> User:
    """
    (Admin) Új felhasználót visz fel a rendszerbe (users.json). [cite: 85]
    """
    try:
        new_user = filehandler.add_user(user)
        return new_user
    except ValueError as e:
        raise HTTPException(status_code=400, detail=str(e))

@routers.post('/addshoppingbag', dependencies=[Depends(check_admin_token)])
def addshoppingbag(userid: int) -> JSONResponse:
    """
    (Admin) Kosarat rendel egy meglévő felhasználóhoz (data.json). [cite: 86]
    """
    user = filereader.get_user_by_id(userid)
    if not user:
        raise HTTPException(status_code=404, detail=f"Felhasználó ({userid}) nem található.")

    baskets_data = filereader.load_data(filereader.DATA_FILE_PATH)
    new_id = 1
    if baskets_data:
        new_id = max(b['id'] for b in baskets_data) + 1
        
    new_basket = Basket(id=new_id, user_id=userid, items=[])

    try:
        filehandler.add_basket(new_basket)
        return JSONResponse(
            content={"message": "Sikeres kosár hozzárendelés."},
            status_code=201 
        )
    except ValueError as e:
        raise HTTPException(status_code=400, detail=str(e))

@routers.get('/user', response_model=User, dependencies=[Depends(check_admin_token)])
def user(userid: int) -> User:
    """(Admin) Egy adott felhasználó adatait adja vissza. [cite: 83]"""
    user_data = filereader.get_user_by_id(userid)
    if not user_data:
        raise HTTPException(status_code=404, detail=f"Felhasználó ({userid}) nem található.")
    return user_data

@routers.get('/users', response_model=List[User], dependencies=[Depends(check_admin_token)])
def users() -> List[User]:
    """(Admin) Visszaadja az összes felhasználót a rendszerből. [cite: 84]"""
    all_users = filereader.get_all_users()
    return all_users

@routers.delete('/deleteallitems', response_model=Basket, dependencies=[Depends(check_admin_token)])
def deleteallitems(userid: int) -> Basket:
    """
    (Admin) Kiüríti egy felhasználó teljes kosarát. 
    (A PDF '/deleteall' néven említi.)
    """
    try:
        empty_basket = filehandler.delete_all_items_from_basket(userid)
        return empty_basket
    except ValueError as e:
        raise HTTPException(status_code=404, detail=str(e))

@routers.delete('/deleteuser/{userid}', dependencies=[Depends(check_admin_token)])
def delete_user(userid: int) -> JSONResponse:
    user = filereader.get_user_by_id(userid)
    if not user:
        raise HTTPException(status_code=404, detail=f"Felhasználó ({userid}) nem található.")
    
    basket = filereader.get_basket_by_user_id(userid)
    if basket and basket.items: 
        raise HTTPException(status_code=400, detail="A felhasználó nem törölhető: a kosara nem üres.")
    
    filehandler.delete_user_by_id(userid)
    
    if basket:
        all_baskets = filereader.load_data(filereader.DATA_FILE_PATH)
        baskets_to_keep = [b for b in all_baskets if b['user_id'] != userid]
        filehandler.save_data(filehandler.DATA_FILE_PATH, baskets_to_keep)

    return JSONResponse(
        content={"message": f"Felhasználó ({userid}) és üres kosara sikeresen törölve."}
    )