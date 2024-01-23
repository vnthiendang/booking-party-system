import { useState } from "react";
import { AdminMenu } from "./AdminMenu";
import { Button } from "antd";
import { MenuOutlined, CloseOutlined } from "@ant-design/icons";

export const AdminSider = () => {
  const [menuState, setMenuState] = useState(true);

  return (
    <div className="Sider">
      <div className="DisplayBtn">
        <Button
          className="LecturerShowBtn"
          icon={menuState ? <CloseOutlined /> : <MenuOutlined />}
          onClick={() => setMenuState(!menuState)}
        ></Button>
      </div>

      {menuState && (
        <span className="menuAnimation">
          <AdminMenu />
        </span>
      )}
    </div>
  );
};
