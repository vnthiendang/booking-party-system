import { UserOutlined } from "@ant-design/icons";
import { Typography, Avatar} from "antd";
import "./NavStyle.css";

const { Text } = Typography;
export const Nav = (props) => {
  //user
  const user = props.user;

  return (
    <div className="Nav">
      <div className="logo">
        {/* Logo */}
        <img
          src="../theBigBoxLogo.png"
          style={Object.assign(
            { height: "39px" },
            { margin: "6px 0 0 0" },
            { padding: 0 }
          )}
          alt="BOOKING PARTY"
        />
      </div>

      {/* User */}
      {user !== null && (
        <div className="User">
          <Text className="Name">{user?.name}</Text>
          {/* <UserOutlined className="Icon" /> */}

          <Avatar
            src={user?.picture}
            icon={<UserOutlined />}
            alt="your avatar"
          ></Avatar>
        </div>
      )}
    </div>
  );
};
