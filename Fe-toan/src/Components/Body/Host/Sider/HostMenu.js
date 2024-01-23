import { useContext, useState } from "react";
import { Data } from "../../Body";
import { Menu} from "antd";
import {
  DropboxOutlined,
  BellFilled,
  HomeFilled,
  SettingFilled,
  CoffeeOutlined,
  MoneyCollectOutlined
} from "@ant-design/icons";
import { useLogOut } from "../../../../Hooks/All/useLogout";

export const HostMenu = () => {
  //Get data from app.js
  const { menuOpt, setMenuOpt, isDarkMode } = useContext(Data);
  const [titleUpdate, setTitleUpdate] = useState(false);

  const LogOutBtn = useLogOut();

  //Item for menu
  const menuItems = [
    { label: "Dashboard", icon: <HomeFilled />, key: "lecturerDashboard" },
    { label: "Packages", icon: <DropboxOutlined />, key: "createdSlot" },
    { label: "Party", icon: <CoffeeOutlined />, key: "locations" },
    {
      label: "",
      icon: (
        <>
          <BellFilled />
          <span style={Object.assign({ padding: "0 7px 0 0" })}>
            Booking 
          </span>
        </>
      ),
      key: "request",
    },
    {
      label: "Revenue",
      icon: <MoneyCollectOutlined />,
      key: "lecturerCfg",
      // children: [
      //   { label: "Locations", icon: <EnvironmentFilled />, key: "locations" },
      //   { label: "Informations", icon: <UserOutlined />, key: "lecturerInformations" },
      // ],
      // type: "group",
    },
    // { label: "Configurations", icon: <ControlFilled />, null, [getItem('Locations', 'location')], "group" },
  ];

  return (
    <>
      <Menu
        mode="inline"
        items={menuItems}
        defaultSelectedKeys="lecturerDashboard"
        selectedKeys={menuOpt}
        onClick={(selectedOpt) => {
          if (selectedOpt.key !== "createdSlot") {
            sessionStorage.removeItem("slotBackupData");
            sessionStorage.removeItem("locationBack");
          }
          setMenuOpt(selectedOpt.key);
        }}
      />

      {/* Logout Btn */}
      <LogOutBtn />
    </>
  );
};
