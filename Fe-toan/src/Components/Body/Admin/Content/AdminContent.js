import { useContext, useState } from "react";
import { Data } from "../../Body";
import { AdminDashboard } from "./Dashboard/AdminDashboard";
import { UsersManage } from "./UsersManage/Users/UsersManage";
import { LecturersManage } from "./UsersManage/HOST/LecturersManage";
import { StudentsManage } from "./UsersManage/Customer/StudentsManage";
import { EditUser } from "./UsersManage/Users/EditUser";
import { AddUser } from "./UsersManage/Users/AddUser";
import { PublicLocations } from "./PublicLocations/PublicLocations";
import { AddLocations } from "./PublicLocations/AddLocations";
import { EditLocations } from "./PublicLocations/EditLocations";
import { EditLecturers } from "./UsersManage/HOST/EditLecturers";
import { EditStudent } from "./UsersManage/Customer/EditStudent";
import { MajorsManage } from "./Revenue/MajorsManage";
import { CreateMajor } from "./Revenue/CreateMajor";
import { EditMajor } from "./Revenue/EditMajor";



export const AdminContent = () => {
  const { menuOpt, setMenuOpt } = useContext(Data);
  const [userEdit, setUserEdit] = useState({});
  const [locationEdit, setLocationEdit] = useState({});
  const [lecturerEdit, setlecturerEdit] = useState({});
  const [studentEdit, setStudentEdit] = useState({});
  const [editMajor, setEditMajor] = useState({});

  return (
    <div className="LecturerContent">
      <div className="LecturerShow">
        {menuOpt === "adminDashboard" ? (
          <AdminDashboard />
        ) : menuOpt === "usersManage" ? (
          <UsersManage setMenuOpt={setMenuOpt} setUserEdit={setUserEdit} />
        ) : menuOpt === "lecturersManage" ? (
          <LecturersManage setlecturerEdit={setlecturerEdit} setMenuOpt={setMenuOpt}/>
        ) : menuOpt === "editLecturers" ? (
          <EditLecturers lecturerEdit={lecturerEdit} setMenuOpt={setMenuOpt}/>
        ) : menuOpt === "studentsManage" ? (
          <StudentsManage setMenuOpt={setMenuOpt} setStudentEdit={setStudentEdit} />
        ) : menuOpt === "editStudent" ? (
          <EditStudent studentEdit={studentEdit} setMenuOpt={setMenuOpt} />
        ) : menuOpt === "majorsManage" ? (
          <MajorsManage setMenuOpt={setMenuOpt} setEditMajor={setEditMajor} />
        ) : menuOpt === "addMajor" ? (
          <CreateMajor setMenuOpt={setMenuOpt} />
        ) : menuOpt === "editMajor" ? (
          <EditMajor setMenuOpt={setMenuOpt} editMajor={editMajor} />
        ) : menuOpt === "publicLocationsManage" ? (
          <PublicLocations setMenuOpt={setMenuOpt} setLocationEdit={setLocationEdit} />
        ) : menuOpt === "addLocationsManage" ? (
          <AddLocations setMenuOpt={setMenuOpt} />
        ) : menuOpt === "editLocationsManage" ? (
          <EditLocations setMenuOpt={setMenuOpt} locationEdit={locationEdit}/>
        ) : menuOpt === "editUser" ? (
          <EditUser setMenuOpt={setMenuOpt} setUserEdit={setUserEdit} userEdit={userEdit} />
        ) : menuOpt === "addUser" ? (
          <AddUser setMenuOpt={setMenuOpt} />
        ) : (
          <></>
        )}
      </div>
    </div>
  );
};
