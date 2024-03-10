import React from "react";
import { HeaderEvent } from "../components";

const LayoutEvent = ({ children }) => {
  return (
    <div>
      <HeaderEvent showBtn={true} />
      {children}
    </div>
  );
};

export default LayoutEvent;
