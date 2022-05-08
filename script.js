function startTime() {
  const today = new Date();
  let h = today.getHours();
  let m = today.getMinutes();
  let s = today.getSeconds();
  m = checkTime(m);
  s = checkTime(s);
  document.getElementById("digital-clock").innerHTML = h + ":" + m + ":" + s;
  setTimeout(startTime, 1000);
}

function checkTime(i) {
  if (i < 10) {
    i = "0" + i;
  } // add zero in front of numbers < 10
  return i;
}

function nameFocus(x) {
  x.style.background = "lightslategray";
  x.style.color = "white";
}

function nameBlur() {
  var x = document.getElementById("name");
  x.style.background = "white";
  x.style.color = "black";
  x.value = x.value.toUpperCase();
}

function textSelected() {
  alert("You selected some text!");
}

function departmentSelected() {
  var x = document.getElementById("department").value;
  document.getElementById("demo").innerHTML = "You selected: " + x;
}

function allowDrop(ev) {
  ev.preventDefault();
}

function drag(ev) {
  ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev) {
  ev.preventDefault();
  var data = ev.dataTransfer.getData("text");
  console.log(document.getElementById(data).dataset.value);
  ev.target.value =
    ev.target.value === ""
      ? document.getElementById(data).dataset.value
      : ev.target.value + ", " + document.getElementById(data).dataset.value;
}

function validate() {
  if (nameInvalid()) {
    return false;
  }

  if (collegeInvalid()) {
    return false;
  }
  if (collegeAddressInvalid()) {
    return false;
  }
  if (ageInvalid()) {
    return false;
  }
  if (contactInvalid()) {
    return false;
  }

  const formData = {
    name: document.registration.name.value,
    college_name: document.registration.college_name.value,
    college_address: document.registration.college_address.value,
    pincode: document.registration.pincode.value,
    age: document.registration.age.value,
    dob: document.registration.dob.value,
    gender: document.registration.gender.value,
    department: document.registration.department.value,
    contact: document.registration.contact.value,
    email: document.registration.email.value,
    skills: document.registration.skills.value,
    hobby1: document.registration.hobby1.value,
    hobby2: document.registration.hobby2.value,
    hobby3: document.registration.hobby3.value,
    filename: document.registration.filename.value,
  };
  const formJSON = JSON.stringify(formData);
  console.log(formJSON);
  localStorage.setItem("formData", formJSON);
}

function nameInvalid() {
  var regex = /^[a-zA-Z]*$/;
  console.log(document.registration.name.value);
  if (!regex.test(document.registration.name.value)) {
    document.registration.name.setCustomValidity(
      "Name should only contain alphabets"
    );
    return true;
  }
  return false;
}

function collegeInvalid() {
  var regex = /^[a-zA-Z]*$/;
  if (!regex.test(document.registration.college_name.value)) {
    document.registration.college_name.setCustomValidity(
      "College name should only contain alphabets"
    );
    return true;
  } else {
    return false;
  }
}

function collegeAddressInvalid() {
  var regex = /^\s*([0-9a-zA-Z]*)\s*$/;
  if (!regex.test(document.registration.college_address.value)) {
    alert("College address should only contain alphanumeric characters");
    return true;
  } else {
    return false;
  }
}

function maxLengthCheck(object) {
  if (object.value.length > object.maxLength)
    alert(`Maximum digits is ${object.maxLength} for ${object.name}`);
  object.value = object.value.slice(0, object.maxLength);
}

function ageInvalid() {
  if (document.registration.age.value > 22) {
    alert("Age should be not be more than 22");
    return true;
  }
  return false;
}

function contactInvalid() {
  var regex = /^[0-9]+$/;
  if (!regex.test(document.registration.contact.value)) {
    document.registration.contact.setCustomValidity(
      "Contact number should only contain digits"
    );
    return true;
  } else {
    return false;
  }
}
