
void main() {
    var           nums  = List.of(1, 2, 3);
    List<Integer> nums2 = List.of(1, 2, 3); // "csomagoló típus"

    // List<int> numsWrong = List.of(1, 2, 3); // fordítási hiba
    List      numsBad   = List.of(1, 2, 3); // lefordul, de nem elég jó

    IO.println(nums);
    IO.println(nums2);
}
