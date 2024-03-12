import React from "react";
import { HeaderEvent } from "../components";

const LayoutEvent = ({ children, showBtn }) => {
  return (
    <div>
      <HeaderEvent showBtn={showBtn} />
      {children}
    </div>
  );
};

export default LayoutEvent;
