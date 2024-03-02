import { Alert, Box, Button, Stack } from "@mui/material";
import React, { useEffect, useState } from "react";
import { LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterMoment } from "@mui/x-date-pickers/AdapterMoment";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { PartyImg } from "../../asset";
import { useParams } from "react-router-dom";
import { ServiceApi } from "../../api";

const timeZoneList = ["7:00am-11:00am", "1:00pm-6:00pm", "6:00pm-11:pm"];
const SelectDate = ({ handleNext, packageDetail, setTime }) => {
  const [dateValue, setDateValue] = useState("");

  const checkTime = async (index) => {
    let startTime = new Date("2024-02-29");
    let ednTime = new Date("2024-02-29");
    if (index === 0) {
      // startTime = '2024-02-29 07:00:00';
      // ednTime = '2024-02-29 07:00:00';
      startTime.setUTCHours(7);
      startTime.setMinutes(0);
      ednTime.setUTCHours(11);
      ednTime.setMinutes(0);
    }
    if (index === 1) {
      // startTime = '2024-02-29 07:00:00';
      // ednTime = '2024-02-29 07:00:00';
      startTime.setUTCHours(13);
      startTime.setMinutes(0);
      ednTime.setUTCHours(17);
      ednTime.setMinutes(0);
    }
    if (index === 1) {
      // startTime = '2024-02-29 07:00:00';
      // ednTime = '2024-02-29 07:00:00';
      startTime.setUTCHours(18);
      startTime.setMinutes(0);
      ednTime.setUTCHours(22);
      ednTime.setMinutes(0);
    }

    const res = await ServiceApi.checkTime({
      packagesId: 8,
      startTime: new Date(
        startTime.toLocaleString("en-US", { timeZone: "Asia/Ho_Chi_Minh" })
      )
        .toISOString()
        .replace(/\.\d{3}Z$/, ""),
      endTime: new Date(
        ednTime.toLocaleString("en-US", { timeZone: "Asia/Ho_Chi_Minh" })
      )
        .toISOString()
        .replace(/\.\d{3}Z$/, ""),
    });
    if (res.data === false) {
      setTime({
        startTime: new Date(
          startTime.toLocaleString("en-US", { timeZone: "Asia/Ho_Chi_Minh" })
        )
          .toISOString()
          .replace(/\.\d{3}Z$/, ""),
        endTime: new Date(
          ednTime.toLocaleString("en-US", { timeZone: "Asia/Ho_Chi_Minh" })
        )
          .toISOString()
          .replace(/\.\d{3}Z$/, ""),
      });
      handleNext();
    }
  };

  return (
    <LocalizationProvider dateAdapter={AdapterMoment}>
      <div>
        {/* Pick a Date :
        <Box
          sx={{
            margin: "10px 0",
          }}
        >
          <DatePicker
            value={dateValue}
            onChange={(value) => setDateValue(value)}
          />
        </Box> */}
        <Stack direction={"column"}>
          <p>Select an item</p>
          <Alert severity="info">
            Pick an available time that you want to reserve next to the desired
            item, then click Next Step.
          </Alert>
          {packageDetail && (
            <CartItem item={packageDetail} checkTime={checkTime} />
          )}
        </Stack>
      </div>
    </LocalizationProvider>
  );
};

export default SelectDate;

const CartItem = ({ item, checkTime }) => {
  /**
       * capacity 
description
packageName

price

services :[2, 3]
venue :"ThuDuc"
       */
  return (
    <Stack
      direction="column"
      sx={{
        margin: "20px 0",
      }}
    >
      <p>{item?.packageName}</p>
      <Stack direction="row" spacing={5}>
        <img src={PartyImg} alt="party img" width={128} height={128} />
        <p>
          Location :{item?.venue}
          <br />
          Total Capacity: {item?.capacity}
        </p>
        <Stack direction={"column"}>
          <p>Time Zone : MST</p>
          <Stack direction={"row"} spacing={3}>
            {timeZoneList?.map((item, index) => (
              <Button
                key={index}
                variant="contained"
                sx={{
                  background: "#f1f1f1",
                  color: "black",
                }}
                onClick={() => checkTime(index)}
              >
                {item}
              </Button>
            ))}
          </Stack>
        </Stack>
      </Stack>
      <hr />
    </Stack>
  );
};
