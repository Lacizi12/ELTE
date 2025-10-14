# 1. feladat 
mx = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
flat = [x for row in mx for x in row]
print(flat)

# 2. feladat 
coords = [(x, y) for x in range(2) for y in range(3)]
print(coords)

# 3. feladat

files = ["py.py", "py.py.txt",
"hello.docx", "music.json", "names.txt", "doctor_x.xlsx", "abc.json"]
py_files = [f for f in files if f.endswith('.py')]
print(py_files)

ext = {f.split('.')[-1] for f in files}
print(ext)
counts = Counter(Path(f).suffix for f in files)
print(dict(counts))  # {'.py': 1, '.txt': 2, '.docx': 1, '.json': 2, '.xlsx': 1}

# d) csoportosítás kiterjesztés szerint
groups = defaultdict(list)
for f in files:
    groups[Path(f).suffix].append(f)
print(dict(groups))