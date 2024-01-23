import { useContext } from "react";
import { Data } from "../../Body";
import { Menu } from "antd";
import {
  HomeFilled,
  SolutionOutlined,
  UserOutlined,
  TeamOutlined,
  ProfileOutlined,
  MoneyCollectOutlined
} from "@ant-design/icons";
import { useLogOut } from "../../../../Hooks/All/useLogout";

export const AdminMenu = () => {
  const { menuOpt, setMenuOpt } = useContext(Data);
  const LogOutBtn = useLogOut();

  const menuItems = [
    { label: "Dashboard", icon: <HomeFilled />, key: "adminDashboard" },
    { label: "Revenue", icon: <MoneyCollectOutlined />, key: "majorsManage" },
    {
      label: "Profile",
      key: null,
      children: [
        { label: "Users", icon: <TeamOutlined />, key: "usersManage" },
        {
          label: "Party Host",
          icon: <SolutionOutlined />,
          key: "lecturersManage",
        },
        { label: "Customer", 
        icon: <UserOutlined />, 
        key: "studentsManage" 
      },
      ],
      type: "group",
    },
  ];

  return (
    <span>
      <Menu
        mode="inline"
        items={menuItems}
        defaultSelectedKeys="lecturerDashboard"
        selectedKeys={menuOpt}
        onClick={(selectedOpt) => {
          setMenuOpt(selectedOpt.key);
        }}
      />
      <LogOutBtn />
    </span>
  );
};
