import React, { useContext, useState } from "react";
import {
  Button,
  Form,
  Input,
  Typography,
  message,
  Row,
  Col,
} from "antd";
import axios from "axios";
import { Data } from "../Body";
import "./LoginStyle.css";
import { useAccountStatus } from "../../../Hooks/All/useAccountStatus";

export const Login = () => {
  const { Title } = Typography;
  const { setUser, setRole } = useContext(Data);
  const validStatus = useAccountStatus();
  const [setDisableAccount] = useState(false);


  const getRole = (role) => {
    switch (role) {
      case 'ADMIN':
        return "ADMIN";
      case 'CUSTOMER':
        return "CUSTOMER";
      case 'HOST':
        return "HOST";
      default:
        return "";
    }
  };

  const [loading, setLoading] = useState(false);


  const handleLoginByEmailFinish = (data) => {

    setLoading(true);
    axios
      .post(
        `http://localhost:8080/booking/auth/authenticate`, data
      )

      .then((response) => {
        return response.data;
      })

      .then((userData) => {
        if (
          userData.access_token === data.email &&
          userData.password === data.password
        ) {
          const finalUser = {
            name: userData.name,
            email: userData.email,
            phone: userData.phone,
            role: getRole(userData.role),
          };
          console.log(userData);
          //check account 
          validStatus(finalUser, setUser, setRole, setDisableAccount);
        } else {
          message.error("Invalid email or password!");
        }
      })
      .catch((err) => {
        message.error("Invalid email or password!");
        console.log(err);
      })
      .finally(() => setLoading(false));
  };



  const [clickSubmit, setClickSubmit] = useState(0);
  setTimeout(() => {
    clickSubmit > 0 && setClickSubmit(clickSubmit - 1);
  }, 3000);
  //checker
  const handleSubmitAntispam = (data) => {
    clickSubmit === 2 && message.error("Please try again in 3 seconds");
    clickSubmit < 3 && setClickSubmit(clickSubmit + 1);
    if (clickSubmit < 2) {
      handleLoginByEmailFinish(data);
    }
  };

  return (
    <div className="backgroundLogin">
      <Form className="loginForm" onFinish={handleSubmitAntispam}>
        <Title className="loginTitle" level={3}>
          Login Form
        </Title>
        <Row>
          <Col xs={7}>
            <Title
              level={5}
              style={Object.assign(
                { margin: "0 0 32px 0" },
                { padding: 0 },
                { textAlign: "left" }
              )}
            >
              Email<span style={{ color: "red" }}> *</span>
            </Title>
          </Col>
          <Col xs={1}></Col>
          <Col xs={16}>
            <Form.Item
              name="email"
              style={Object.assign({ margin: 0 }, { padding: 0 })}
              rules={[{ required: true }]}
            >
              <Input />
            </Form.Item>
          </Col>
        </Row>
        <Row>
          <Col xs={7}>
            <Title
              level={5}
              style={Object.assign(
                { margin: "0 0 32px 0" },
                { padding: 0 },
                { textAlign: "left" }
              )}
            >
              Password<span style={{ color: "red" }}> *</span>
            </Title>
          </Col>
          <Col xs={1}></Col>
          <Col xs={16}>
            <Form.Item
              name="password"
              style={Object.assign({ margin: 0 }, { padding: 0 })}
              rules={[{ required: true }]}
            >
              <Input.Password />
            </Form.Item>
          </Col>
        </Row>
        <Form.Item>
          <Button
            loading={loading}
            className="loginBtn"
            type="primary"
            htmlType="submit"
          >
            Login
          </Button>
        </Form.Item>
      </Form>
      <ul className="background">
        <li className="bg1"></li>
        <li className="bg1"></li>
        <li className="bg1"></li>
        <li className="bg2"></li>
        <li className="bg2"></li>
        <li className="bg2"></li>
        <li className="bg3"></li>
        <li className="bg3"></li>
        <li className="bg3"></li>
      </ul>
    </div>
  );
};
