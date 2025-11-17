import json
from typing import Dict, Any, List, Optional
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

def get_all_users() -> List[User]:

    users_data = load_data(USERS_FILE_PATH)
    # Átalakítjuk a szótárak listáját User modellek listájává
    return [User.model_validate(user) for user in users_data]

def get_user_by_id(user_id: int) -> Optional[User]:

    users_data = load_data(USERS_FILE_PATH)
    for user_dict in users_data:
        if user_dict['id'] == user_id:
            return User.model_validate(user_dict)
    return None

def get_basket_by_user_id(user_id: int) -> Optional[Basket]:
    baskets_data = load_data(DATA_FILE_PATH)
    for basket_dict in baskets_data:
        if basket_dict['user_id'] == user_id:
            return Basket.model_validate(basket_dict)
    return None

def get_total_price_of_basket(user_id: int) -> float:
    basket = get_basket_by_user_id(user_id)
    
    if not basket:
        return 0.0
    
    total_price = 0.0
    for item in basket.items:
        total_price += item.price * item.quantity
        
    return total_price