import { useState } from "react";
import { HostMenu } from "./HostMenu";
import { Button } from "antd";
import { MenuOutlined, CloseOutlined } from "@ant-design/icons";

export const HostSlider = () => {
  // Set menu state
  const [menuState, setMenuState] = useState(true);
  return (
      <div className="Sider">
        {/* Button */}
        <div className="DisplayBtn">
          {/* show/unshow menu */}
          <Button
            className="LecturerShowBtn"
            icon={menuState ? <CloseOutlined /> : <MenuOutlined />}
            onClick={() => setMenuState(!menuState)}
          ></Button>
        </div>

        {/* Menu */}
        {menuState && (
          <span className="menuAnimation">
            {/* Menu */}
            <HostMenu />
          </span>
        )}
      </div>
  );
};
