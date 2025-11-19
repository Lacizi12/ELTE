from fastapi import FastAPI, Request
from fastapi.responses import HTMLResponse
from fastapi.templating import Jinja2Templates
from fastapi.staticfiles import StaticFiles

app = FastAPI()

# Jinja2 sablonok elérési útja
templates = Jinja2Templates(directory="templates")

@app.get("/", response_class=HTMLResponse)
async def index(request: Request):
    user = {"name": "Hallgató"}
    return templates.TemplateResponse("index.html", {"request": request, "user": user})

@app.get("/about", response_class=HTMLResponse)
async def about(request: Request):
    info = {"title": "Információk", "content": "Ez az oldal bemutatja az alkalmazást."}
    return templates.TemplateResponse("about.html", {"request": request, "info": info})
