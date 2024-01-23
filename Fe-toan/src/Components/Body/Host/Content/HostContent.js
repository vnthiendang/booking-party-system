import { HostDashboard } from "./Home/HostDashboard";
import { useContext, useState } from "react";
import { Data } from "../../Body";



export const HostContent = () => {
  const { menuOpt, setMenuOpt } = useContext(Data);
  return (
        <div className="LecturerContent">
          <div className="LecturerShow">
            {/* CreatedSlot */}
            {menuOpt === "lecturerDashboard" ? (
              <HostDashboard />
            ) :(
              <></>
            )}
          </div>
        </div>
  );
};
