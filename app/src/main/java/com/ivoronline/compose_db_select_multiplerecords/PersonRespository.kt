package com.ivoronline.compose_db_select_multiplerecords

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*

class PersonRepository(private val productDao: PersonDao) {

  //PROPERTIES
  private val coroutineScope = CoroutineScope(Dispatchers.Main)
  var person  : Person? by mutableStateOf(null)       //Returned Person. Possible none.
  var persons : List<Person>? by mutableStateOf(null) //Returned Person. Possible none.

  //INSERT PERSON
  fun insertPerson(person: Person) {
    coroutineScope.launch(Dispatchers.IO) {
      productDao.insertPerson(person)
    }
  }

  //DELETE PERSON
  fun deletePerson(name: String) {
    coroutineScope.launch(Dispatchers.IO) {
      productDao.deletePerson(name)
    }
  }

  //SELECT PERSON
  fun findPersonById(id: Int) {
    coroutineScope.launch(Dispatchers.Main) {
      person = asyncFindPersonById(id).await()
    }
  }
  private fun asyncFindPersonById(id: Int): Deferred<Person?> =
    coroutineScope.async(Dispatchers.IO) {
      return@async productDao.findPersonById(id)
    }

  //SELECT PERSONS
  fun findPersonsByName(name: String) {
    coroutineScope.launch(Dispatchers.Main) {
      persons = asyncPersonsByName(name).await()
    }
  }
  private fun asyncPersonsByName(name: String): Deferred<List<Person>?> =
    coroutineScope.async(Dispatchers.IO) {
      return@async productDao.findPersonsByName(name)
    }

}

