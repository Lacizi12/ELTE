marks = {
    "Anna": [5, 7.8],
    "Bea": [9.2, 10],
    "Csaba": [7.8, 9],
    "Dóra": [10, 10],
    "Endre": [8, 7.5],
    "Ferenc": [0, 5]
}

# a)
print("Hallgatói átlagpontszámok:")
averages = {}
for name, scores in marks.items():
    avg = sum(scores) / len(scores)
    averages[name] = avg
    print(f"{name}: {avg:.2f}")

# b)
group_avg = sum(averages.values()) / len(averages)
print(f"\nCsoport átlaga: {group_avg:.2f}\n")

# c)
print("Jegyek:")
for name, avg in averages.items():
    percent = avg * 10  # mivel max 10 pont -> szorozzuk 10-zel
    if percent >= 90:
        grade = 5
    elif percent >= 78:
        grade = 4
    elif percent >= 64:
        grade = 3
    elif percent >= 50:
        grade = 2
    else:
        grade = 1
    print(f"{name}: jegy: {grade}")
