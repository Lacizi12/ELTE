<?php
include_once("storage.php");
//betöltöd az általános adattárolási osztályt. Mivel a ContactStorage ebből származik, örökölni fogja az összes CRUD metódust.

class UserStorage extends Storage {
  public function __construct() {
    parent::__construct(new JsonIO('users.json'));
  }
}