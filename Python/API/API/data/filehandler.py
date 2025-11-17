import json
from typing import Dict, Any, List
from schemas.schema import User, Basket, Item

USERS_FILE_PATH = "data/users.json"
DATA_FILE_PATH = "data/data.json"

def load_data(file_path: str) -> List[Dict[str, Any]]:
    try:
        with open(file_path, "r", encoding="utf-8") as file:
            data = json.load(file)
            return data if isinstance(data, list) else []
    except (FileNotFoundError, json.JSONDecodeError):
        return []

def save_data(file_path: str, data: List[Dict[str, Any]]) -> None:
    with open(file_path, "w", encoding="utf-8") as file:
        json.dump(data, file, indent=4, ensure_ascii=False)

def add_user(user: User) -> User:
    users = load_data(USERS_FILE_PATH)
    for existing_user in users:
        if existing_user['id'] == user.id:
            raise ValueError(f"Felhasználó ID {user.id} már létezik.")
        if existing_user['email'] == user.email:
            raise ValueError(f"Email cím {user.email} már foglalt.")
            
    users.append(user.model_dump())
    save_data(USERS_FILE_PATH, users)
    return user

def delete_user_by_id(user_id: int) -> bool:

    users = load_data(USERS_FILE_PATH)
    user_to_delete = None
    
    for user in users:
        if user['id'] == user_id:
            user_to_delete = user
            break
    
    if user_to_delete:
        users.remove(user_to_delete)
        save_data(USERS_FILE_PATH, users)
        return True
    return False


def add_basket(basket: Basket) -> Basket:
    baskets = load_data(DATA_FILE_PATH)
    
    for existing_basket in baskets:
        if existing_basket['id'] == basket.id:
            raise ValueError(f"Kosár ID {basket.id} már létezik.")
        if existing_basket['user_id'] == basket.user_id:
            raise ValueError(f"Ennek a felhasználónak ({basket.user_id}) már van kosara.")
            
    baskets.append(basket.model_dump())
    save_data(DATA_FILE_PATH, baskets)
    return basket

def add_item_to_basket(user_id: int, item: Item) -> Basket:
    baskets = load_data(DATA_FILE_PATH)
    basket_found = False
    updated_basket = None

    for basket_dict in baskets:
        if basket_dict['user_id'] == user_id:
            basket_found = True
            item_exists = False
            for existing_item in basket_dict['items']:
                if existing_item['item_id'] == item.item_id:
                    existing_item['quantity'] += item.quantity
                    item_exists = True
                    break
            if not item_exists:
                basket_dict['items'].append(item.model_dump())
            
            updated_basket = basket_dict
            break

    if not basket_found:
        raise ValueError(f"Nem található kosár a {user_id} felhasználóhoz.")

    save_data(DATA_FILE_PATH, baskets)
    return Basket.model_validate(updated_basket)


def update_item_in_basket(user_id: int, item_id: int, updated_item: Item) -> Basket:
    baskets = load_data(DATA_FILE_PATH)
    basket_found = False
    item_found = False
    basket_to_return = None

    for basket_dict in baskets:
        if basket_dict['user_id'] == user_id:
            basket_found = True
            for i, item_dict in enumerate(basket_dict['items']):
                if item_dict['item_id'] == item_id:
                    updated_item_data = updated_item.model_dump()
                    updated_item_data['item_id'] = item_id 
                    basket_dict['items'][i] = updated_item_data
                    item_found = True
                    basket_to_return = basket_dict
                    break
            break
    
    if not basket_found:
        raise ValueError(f"Nem található kosár a {user_id} felhasználóhoz.")
    if not item_found:
        raise ValueError(f"Nem található termék ({item_id}) a kosárban.")

    save_data(DATA_FILE_PATH, baskets)
    return Basket.model_validate(basket_to_return)


def delete_item_from_basket(user_id: int, item_id: int) -> Basket:
    baskets = load_data(DATA_FILE_PATH)
    basket_found = False
    item_to_delete = None
    basket_to_return = None

    for basket_dict in baskets:
        if basket_dict['user_id'] == user_id:
            basket_found = True
            basket_to_return = basket_dict
            for item_dict in basket_dict['items']:
                if item_dict['item_id'] == item_id:
                    item_to_delete = item_dict
                    break
            
            if item_to_delete:
                basket_dict['items'].remove(item_to_delete)
            else:
                raise ValueError(f"Nem található termék ({item_id}) a kosárban.")
            break
    
    if not basket_found:
        raise ValueError(f"Nem található kosár a {user_id} felhasználóhoz.")

    save_data(DATA_FILE_PATH, baskets)
    return Basket.model_validate(basket_to_return)


def delete_all_items_from_basket(user_id: int) -> Basket:
    baskets = load_data(DATA_FILE_PATH)
    basket_found = False
    basket_to_return = None

    for basket_dict in baskets:
        if basket_dict['user_id'] == user_id:
            basket_found = True
            basket_dict['items'] = []
            basket_to_return = basket_dict
            break

    if not basket_found:
        raise ValueError(f"Nem található kosár a {user_id} felhasználóhoz.")
    
    save_data(DATA_FILE_PATH, baskets)
    return Basket.model_validate(basket_to_return)