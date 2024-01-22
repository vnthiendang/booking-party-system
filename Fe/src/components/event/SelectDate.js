import { Alert, Box, Button, Stack } from "@mui/material";
import React from "react";
import { LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterMoment } from "@mui/x-date-pickers/AdapterMoment";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { PartyImg } from "../../asset";

const timeZoneList = ["10:00am", "12:00pm", "2:00pm", "4:00pm", "6:00pm"];
const SelectDate = () => {
  return (
    <LocalizationProvider dateAdapter={AdapterMoment}>
      <div>
        Pick a Date :
        <Box
          sx={{
            margin: "10px 0",
          }}
        >
          <DatePicker />
        </Box>
        <Stack direction={"column"}>
          <p>Select an item</p>
          <Alert severity="info">
            Pick an available time that you want to reserve next to the desired
            item, then click Next Step.
          </Alert>
          {Array.from({ length: 5 }, (value, index) => index + 1)?.map(
            (item, index) => (
              <CartItem key={index} />
            )
          )}
        </Stack>
      </div>
    </LocalizationProvider>
  );
};

export default SelectDate;

const CartItem = ({ item }) => {
  return (
    <Stack
      direction="column"
      sx={{
        margin: "20px 0",
      }}
    >
      <p>Party Room</p>
      <Stack direction="row" spacing={5}>
        <img src={PartyImg} alt="party img" width={128} height={128} />
        <p>
          Location : <br />
          The Big Box - Family Entertainment Hub Unit 120 930 64 Ave NE,
          <br />
          Calgary, T2E8S8
          <br />
          Total Capacity: 40
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
