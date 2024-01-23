import { Col, Row } from "antd";
import { HostSlider } from "./Sider/HostSlider";
import { HostContent } from "./Content/HostContent";
import { useEffect } from "react";

export const Host = ({setMenuOpt}) => {
  useEffect(() => {
    setMenuOpt("lecturerDashboard");
  }, []);
  
  return (
      <Row>
        <Col xs={24} sm={6} md={5} xl={4}>
          <HostSlider />
        </Col>
        <Col xs={24} sm={18} md={19} xl={20}>
          <HostContent />
        </Col>
      </Row>
  );
};
